package net.silentchaos512.loginar.block.urn;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

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
        if (tintIndex == 0) {
            // Main body (clay)
            return UrnHelper.getClayColor(stack);
        } else if (tintIndex == 1) {
            // Decorative gem
            return UrnHelper.getGemColor(stack);
        }
        return Color.VALUE_WHITE;
    }

    public ItemStack makeStack(int clayColor, int gemColor) {
        ItemStack stack = new ItemStack(this);
        stack.set(LsDataComponents.URN_DATA, new UrnData(this.type, clayColor, gemColor));
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
        var data = stack.get(LsDataComponents.URN_DATA);
        if (data == null) return;

        var urn = level.getBlockEntity(pos, this.type.blockEntity().get()).orElseThrow();
        urn.refreshData(data);
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

        var urnData = stack.get(LsDataComponents.URN_DATA);
        if (urnData == null) return;

        tooltipUrnData(tooltip, urnData);
        tooltipUpgradesList(stack, tooltip, urnData);
        tooltipItemsList(tooltip, urnData);
    }

    private static void tooltipUrnData(List<Component> tooltip, UrnData urnData) {
        tooltip.add(Component.literal("Type: ").append(urnData.urnType().toString()));
        var clayColor = urnData.clayColor();
        var clayColorText = Component.literal("Clay Color: ")
                .append(TextUtil.withColor(Component.literal(Color.format(clayColor)), clayColor));
        tooltip.add(clayColorText);
        var gemColor = urnData.gemColor();
        var gemColorText = Component.literal("Gem Color: ")
                .append(TextUtil.withColor(Component.literal(Color.format(gemColor)), gemColor));
        tooltip.add(gemColorText);
    }

    private static void tooltipUpgradesList(ItemStack stack, List<Component> tooltip, UrnData urnData) {
        tooltip.add(TextUtil.misc("urn.upgrades", UrnHelper.getUpgradeCount(stack), UrnHelper.getMaxUpgradeCount(stack)));
        for (ItemStack upgrade : urnData.upgrades()) {
            if (!upgrade.isEmpty()) {
                tooltip.add(Component.literal("- ").append(upgrade.getHoverName()).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }

    private static void tooltipItemsList(List<Component> tooltip, UrnData urnData) {
        List<ItemStack> items = urnData.items();
        int i = 0;
        int j = 0;

        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                ++j;
                if (i <= 4) {
                    ++i;
                    MutableComponent mutablecomponent = item.getHoverName().copy();
                    mutablecomponent.append(" x").append(String.valueOf(item.getCount()));
                    tooltip.add(Component.translatable("container.shulkerBox.itemCount", item.getHoverName(), item.getCount()));
                }
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
        ItemStack itemstack = super.getCloneItemStack(state, target, level, pos, player);
        level.getBlockEntity(pos, this.type.blockEntity().get()).ifPresent(urn ->
                itemstack.set(LsDataComponents.URN_DATA, urn.getUrnData())
        );
        return itemstack;
    }
}
