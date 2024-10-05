package net.silentchaos512.loginar.block.urn;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsSounds;
import net.silentchaos512.loginar.setup.UrnTypes;
import net.silentchaos512.loginar.util.TextUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class LoginarUrnBlock extends BaseEntityBlock {
    public static final MapCodec<LoginarUrnBlock> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(UrnTypes.CODEC.optionalFieldOf("urn_type").forGetter(block -> Optional.of(block.type)), propertiesCodec())
                    .apply(builder, (urnType, properties) -> new LoginarUrnBlock(urnType.orElse(UrnTypes.MEDIUM), properties))
    );

    public static final ResourceLocation CONTENTS = ResourceLocation.withDefaultNamespace("contents");

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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, this.type.blockEntity().get(), LoginarUrnBlockEntity::tick);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static int getBlockColor(BlockState state, @Nullable BlockGetter level, @Nullable BlockPos pos, int tintIndex) {
        if (level != null && pos != null) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof LoginarUrnBlockEntity urn) {
                if (tintIndex == 0) {
                    // Main body (clay)
                    return urn.getClayColor().getColor();
                } else if (tintIndex == 1) {
                    // Decorative gem
                    return urn.getGemColor().getColor();
                }
            }
        }
        return Color.VALUE_WHITE;
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 0) {
            // Main body (clay)
            return UrnHelper.getClayColor(stack).getColor();
        } else if (tintIndex == 1) {
            // Decorative gem
            return UrnHelper.getGemColor(stack).getColor();
        }
        return Color.VALUE_WHITE;
    }

    public ItemStack makeStack(@Nullable Color clayColor, @Nullable Color gemColor) {
        ItemStack stack = new ItemStack(this);
        if (clayColor != null) {
            stack.set(LsDataComponents.URN_CLAY_COLOR, clayColor);
        }
        if (gemColor != null) {
            stack.set(LsDataComponents.URN_GEM_COLOR, gemColor);
        }
        return stack;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof LoginarUrnBlockEntity urn) {
                player.openMenu(urn, buf -> buf.writeByte(this.type.inventorySize()));
//                player.awardStat(Stats.OPEN_SHULKER_BOX);
                PiglinAi.angerNearbyPiglins(player, true);

                level.playSound(null, pos, LsSounds.URN_OPEN.get(), SoundSource.BLOCKS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);

                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof LoginarUrnBlockEntity urn) {
            if (!level.isClientSide && player.isCreative() && !urn.isEmpty()) {
                ItemStack itemstack = new ItemStack(this);
                itemstack.applyComponents(urn.collectComponents());

                ItemEntity itementity = new ItemEntity(level, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            } else {
                urn.unpackLootTable(player);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder context) {
        BlockEntity blockentity = context.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof LoginarUrnBlockEntity urn) {
            context = context.withDynamicDrop(CONTENTS, consumer -> {
                for (int i = 0; i < urn.getContainerSize(); ++i) {
                    consumer.accept(urn.getItem(i));
                }

            });
        }

        return super.getDrops(state, context);
    }

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
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        level.getBlockEntity(pos, this.type.blockEntity().get()).orElseThrow().setDataFromPlacedItem(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, tooltipContext, tooltip, flags);
        var customData = stack.get(DataComponents.BLOCK_ENTITY_DATA);

        if (customData != null) {
            CompoundTag tags = customData.copyTag();
            if (tags.contains("LootTable", 8)) {
                tooltip.add(Component.literal("???????"));
            }
        }

        tooltipUrnData(stack, tooltip);
        tooltipUpgradesList(stack, tooltip);
        tooltipItemsList(stack, tooltip);
    }

    private static void tooltipUrnData(ItemStack stack, List<Component> tooltip) {
        var clayColor = UrnHelper.getClayColor(stack);
        var clayColorText = TextUtil.withColor(Component.literal(clayColor.format()), clayColor);
        tooltip.add(TextUtil.misc("urn.clayColor", clayColorText));

        var gemColor = UrnHelper.getGemColor(stack);
        var gemColorText = TextUtil.withColor(Component.literal(gemColor.format()), gemColor);
        tooltip.add(TextUtil.misc("urn.gemColor", gemColorText));
    }

    private static void tooltipUpgradesList(ItemStack stack, List<Component> tooltip) {
        tooltip.add(TextUtil.misc("urn.upgrades", UrnHelper.getUpgradeCount(stack), UrnHelper.getMaxUpgradeCount(stack)));
        var upgrades = stack.getOrDefault(LsDataComponents.URN_UPGRADES, ItemContainerContents.EMPTY);
        for (int i = 0; i < upgrades.getSlots(); ++i) {
            ItemStack upgrade = upgrades.getStackInSlot(i);
            if (!upgrade.isEmpty()) {
                tooltip.add(Component.literal("- ").append(upgrade.getHoverName()).withStyle(ChatFormatting.ITALIC));
            }
        }
    }

    private static void tooltipItemsList(ItemStack stack, List<Component> tooltip) {
        int i = 0;
        int j = 0;

        for (ItemStack item : stack.getOrDefault(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.EMPTY).nonEmptyItems()) {
            ++j;
            if (i <= 4) {
                ++i;
                tooltip.add(Component.translatable("container.shulkerBox.itemCount", item.getHoverName(), item.getCount()));
            }
        }

        if (j - i > 0) {
            tooltip.add(Component.translatable("container.shulkerBox.more", j - i).withStyle(ChatFormatting.ITALIC));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.type.blockShape();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAnalogOutputSignal(BlockState p_60457_) {
        return true;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        ItemStack ret = super.getCloneItemStack(state, target, level, pos, player);
        level.getBlockEntity(pos, this.type.blockEntity().get()).ifPresent(urn -> {
                    ret.set(LsDataComponents.URN_CLAY_COLOR, urn.getClayColor());
                    ret.set(LsDataComponents.URN_GEM_COLOR, urn.getGemColor());
                    ret.set(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.fromItems(urn.getItems()));
                    ret.set(LsDataComponents.URN_UPGRADES, ItemContainerContents.fromItems(urn.getUpgrades()));
                }
        );
        return ret;
    }
}
