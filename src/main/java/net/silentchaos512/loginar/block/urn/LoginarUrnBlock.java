package net.silentchaos512.loginar.block.urn;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.silentchaos512.loginar.setup.UrnTypes;
import net.silentchaos512.utils.Color;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LoginarUrnBlock extends BaseEntityBlock {
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 15, 12);

    private final UrnTypes type;

    public LoginarUrnBlock(UrnTypes type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public UrnTypes getType() {
        return type;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LoginarUrnBlockEntity(this.type, pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static int getBlockColor(BlockState state, @Nullable BlockGetter level, @Nullable BlockPos pos, int tintIndex) {
        if (level != null && pos != null) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof LoginarUrnBlockEntity) {
                LoginarUrnBlockEntity urn = (LoginarUrnBlockEntity) blockEntity;

                if (tintIndex == 0) {
                    // Main body (clay)
                    return urn.getClayColor();
                } else if (tintIndex == 1) {
                    // Decorative gem
                    return urn.getGemColor();
                }
            }
        }
        return Color.VALUE_WHITE;
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        // TODO
        if (tintIndex == 0) {
            // Main body (clay)
            return 0x985F45;
        } else if (tintIndex == 1) {
            return 0x33EBCB;
        }
        return Color.VALUE_WHITE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof LoginarUrnBlockEntity) {
                LoginarUrnBlockEntity urn = (LoginarUrnBlockEntity) blockentity;
//                player.openMenu(urn);
                NetworkHooks.openScreen((ServerPlayer) player, urn, buf -> buf.writeByte(this.type.inventorySize()));
//                player.awardStat(Stats.OPEN_SHULKER_BOX);
                PiglinAi.angerNearbyPiglins(player, true);

                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof LoginarUrnBlockEntity urn) {
            if (!level.isClientSide && player.isCreative() && !urn.isEmpty()) {
                ItemStack itemstack = new ItemStack(this);
                blockentity.saveToItem(itemstack);
                if (urn.hasCustomName()) {
                    itemstack.setHoverName(urn.getCustomName());
                }

                ItemEntity itementity = new ItemEntity(level, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            } else {
                urn.unpackLootTable(player);
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder context) {
        BlockEntity blockentity = context.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof LoginarUrnBlockEntity urn) {
            context = context.withDynamicDrop(CONTENTS, (p_56218_, p_56219_) -> {
                for (int i = 0; i < urn.getContainerSize(); ++i) {
                    p_56219_.accept(urn.getItem(i));
                }

            });
        }

        return super.getDrops(state, context);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof LoginarUrnBlockEntity) {
                ((LoginarUrnBlockEntity) blockentity).setCustomName(stack.getHoverName());
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state1, Level level, BlockPos pos, BlockState state2, boolean p_56238_) {
        if (!state1.is(state2.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof LoginarUrnBlockEntity) {
                level.updateNeighbourForOutputSignal(pos, state1.getBlock());
            }

            super.onRemove(state1, level, pos, state2, p_56238_);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, level, tooltip, flags);
        CompoundTag compoundtag = BlockItem.getBlockEntityData(stack);
        if (compoundtag != null) {
            if (compoundtag.contains("LootTable", 8)) {
                tooltip.add(Component.literal("???????"));
            }

            if (compoundtag.contains("Items", 9)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
                ContainerHelper.loadAllItems(compoundtag, nonnulllist);
                int i = 0;
                int j = 0;

                for (ItemStack itemstack : nonnulllist) {
                    if (!itemstack.isEmpty()) {
                        ++j;
                        if (i <= 4) {
                            ++i;
                            MutableComponent mutablecomponent = itemstack.getHoverName().copy();
                            mutablecomponent.append(" x").append(String.valueOf(itemstack.getCount()));
                            tooltip.add(mutablecomponent);
                        }
                    }
                }

                if (j - i > 0) {
                    tooltip.add(Component.translatable("container.shulkerBox.more", j - i).withStyle(ChatFormatting.ITALIC));
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public PushReaction getPistonPushReaction(BlockState p_56265_) {
        return PushReaction.DESTROY;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAnalogOutputSignal(BlockState p_60457_) {
        return true;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack itemstack = super.getCloneItemStack(state, target, level, pos, player);
        level.getBlockEntity(pos, this.type.blockEntity().get()).ifPresent(urn -> {
            urn.saveToItem(itemstack);
        });
        return itemstack;
    }
}
