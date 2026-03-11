package net.silentchaos512.loginar.block.urn;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.UrnTypes;
import net.silentchaos512.loginar.util.TextUtil;

import java.util.function.Consumer;

public class LoginarUrnBlockItem extends BlockItem {
    private final UrnTypes urnType;

    public LoginarUrnBlockItem(LoginarUrnBlock block, Properties properties) {
        super(block, properties);
        this.urnType = block.getType();
    }

    public UrnTypes getUrnType() {
        return this.urnType;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (UrnHelper.hasUpgrade(stack, LsItems.BACKPACK_UPGRADE)) {
            if (!level.isClientSide()) {
                openContainer((ServerPlayer) player, stack);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void openContainer(ServerPlayer player, ItemStack stack) {
        player.openMenu(
                new SimpleMenuProvider(
                        (id, inv, z) -> new LoginarUrnBackpackMenu(id, inv, stack),
                        stack.getHoverName()
                ),
                buf -> ItemStack.STREAM_CODEC.encode(buf, stack)
        );
    }

    @Override
    public boolean canFitInsideContainerItems() {
        // Cannot be stored in shulker boxes
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        var customData = stack.get(DataComponents.BLOCK_ENTITY_DATA);

        if (customData != null) {
            CompoundTag tags = customData.copyTagWithoutId();
            if (tags.contains("LootTable")) {
                tooltipAdder.accept(Component.literal("???????"));
            }
        }

        tooltipUrnData(stack, tooltipAdder);
        tooltipUpgradesList(stack, tooltipAdder);
        tooltipItemsList(stack, tooltipAdder);
    }

    private static void tooltipUrnData(ItemStack stack, Consumer<Component> tooltipAdder) {
        int clayColor = UrnHelper.getClayColor(stack).getColor() & 0xFFFFFF;
        var clayColorText = TextUtil.withColor(Component.literal(Color.format(clayColor)), clayColor);
        tooltipAdder.accept(TextUtil.misc("urn.clayColor", clayColorText));

        int gemColor = UrnHelper.getGemColor(stack).getColor() & 0xFFFFFF;
        var gemColorText = TextUtil.withColor(Component.literal(Color.format(gemColor)), gemColor);
        tooltipAdder.accept(TextUtil.misc("urn.gemColor", gemColorText));
    }

    private static void tooltipUpgradesList(ItemStack stack, Consumer<Component> tooltipAdder) {
        tooltipAdder.accept(TextUtil.misc("urn.upgrades", UrnHelper.getUpgradeCount(stack), UrnHelper.getMaxUpgradeCount(stack)));
        var upgrades = stack.getOrDefault(LsDataComponents.URN_UPGRADES, ItemContainerContents.EMPTY);
        for (int i = 0; i < upgrades.getSlots(); ++i) {
            ItemStack upgrade = upgrades.getStackInSlot(i);
            if (!upgrade.isEmpty()) {
                tooltipAdder.accept(Component.literal("- ").append(upgrade.getHoverName()).withStyle(ChatFormatting.ITALIC));
            }
        }
    }

    private static void tooltipItemsList(ItemStack stack, Consumer<Component> tooltipAdder) {
        int i = 0;
        int j = 0;

        for (ItemStack item : stack.getOrDefault(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.EMPTY).nonEmptyItems()) {
            ++j;
            if (i <= 4) {
                ++i;
                tooltipAdder.accept(Component.translatable("item.container.item_count", item.getHoverName(), item.getCount()));
            }
        }

        if (j - i > 0) {
            tooltipAdder.accept(Component.translatable("item.container.more_items", j - i).withStyle(ChatFormatting.ITALIC));
        }
    }
}
