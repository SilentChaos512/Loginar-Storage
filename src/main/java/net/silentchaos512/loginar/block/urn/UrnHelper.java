package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ItemLike;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsTags;
import net.silentchaos512.loginar.setup.UrnTypes;
import net.silentchaos512.loginar.util.ItemStackUtil;

import java.util.List;
import java.util.Objects;

public final class UrnHelper {
    public static final Color DEFAULT_CLAY_COLOR = new Color(0x985F45);
    public static final Color DEFAULT_GEM_COLOR = new Color(0x33EBCB);

    public static final String NBT_CLAY_COLOR = "ClayColor";
    public static final String NBT_GEM_COLOR = "GemColor";
    public static final String NBT_ITEMS = "Items";
    public static final String NBT_UPGRADES = "Upgrades";

    private UrnHelper() {
        throw new IllegalAccessError("Utility class");
    }

    public static boolean isUrn(ItemStack stack) {
        return stack.is(LsTags.Items.URNS);
    }

    public static boolean canUrnStore(ItemStack stack) {
        return !isUrn(stack) && !stack.is(LsTags.Items.URNS_CANNOT_STORE) && stack.getItem().canFitInsideContainerItems();
    }

    public static NonNullList<ItemStack> getItemsMutableCopy(ItemStack stack) {
        var items = stack.getOrDefault(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.EMPTY);
        return ItemStackUtil.createMutableCopyOfList(items, Objects.requireNonNull(UrnTypes.fromItem(stack)).inventorySize());
    }

    public static NonNullList<ItemStack> getUpgradesMutableCopy(ItemStack stack) {
        var upgrades = stack.getOrDefault(LsDataComponents.URN_UPGRADES, ItemContainerContents.EMPTY);
        return ItemStackUtil.createMutableCopyOfList(upgrades, Objects.requireNonNull(UrnTypes.fromItem(stack)).upgradeSlots());
    }

    public static boolean isUpgrade(ItemStack stack) {
        return stack.is(LsTags.Items.URN_UPGRADES);
    }

    public static boolean hasUpgrade(ItemStack urn, ItemLike upgrade) {
        var upgrades = urn.getOrDefault(LsDataComponents.URN_UPGRADES, ItemContainerContents.EMPTY);

        for (int i = 0; i < upgrades.getSlots(); ++i) {
            ItemStack item = upgrades.getStackInSlot(i);
            if (item.is(upgrade.asItem())) {
                return true;
            }
        }
        return false;
    }

    public static int getUpgradeCount(ItemStack urn) {
        var upgrades = urn.getOrDefault(LsDataComponents.URN_UPGRADES, ItemContainerContents.EMPTY);
        return (int) upgrades.stream().filter(s -> !s.isEmpty()).count();
    }

    public static int getMaxUpgradeCount(ItemStack urn) {
        UrnTypes type = UrnTypes.fromItem(urn);
        if (type != null) {
            return type.upgradeSlots();
        }
        return 0;
    }

    public static Color getClayColor(ItemStack stack) {
        return stack.getOrDefault(LsDataComponents.URN_CLAY_COLOR, DEFAULT_CLAY_COLOR);
    }

    public static void setClayColor(ItemStack stack, Color color) {
        stack.set(LsDataComponents.URN_CLAY_COLOR, color);
    }

    public static void setClayColor(ItemStack stack, int color) {
        setClayColor(stack, new Color(color));
    }

    public static Color getGemColor(ItemStack stack) {
        return stack.getOrDefault(LsDataComponents.URN_GEM_COLOR, DEFAULT_GEM_COLOR);
    }

    public static void setGemColor(ItemStack stack, Color color) {
        stack.set(LsDataComponents.URN_GEM_COLOR, color);
    }

    public static void setGemColor(ItemStack stack, int color) {
        setGemColor(stack, new Color(color));
    }

    public static boolean tryAddItem(LoginarUrnBlockEntity urn, ItemStack item) {
        if (!UrnHelper.canUrnStore(item)) {
            return false;
        }

        List<ItemStack> list = urn.getItems();

        for (int slot = 0; slot < list.size(); ++slot) {
            ItemStack stackInSlot = list.get(slot);
            if (!stackInSlot.isEmpty() && !ItemStack.isSameItemSameComponents(item, stackInSlot)) {
                continue;
            }

            if (!stackInSlot.isEmpty()) {
                int amountCanFit = Math.min(item.getCount(), stackInSlot.getMaxStackSize() - stackInSlot.getCount());
                if (amountCanFit <= 0) {
                    continue;
                }
                stackInSlot.setCount(stackInSlot.getCount() + amountCanFit);
                item.setCount(item.getCount() - amountCanFit);

                list.set(slot, stackInSlot);
            } else {
                list.set(slot, item.copy());
                item.setCount(0);
            }

            return true;
        }

        return false;
    }

    public static boolean tryAddUpgrade(ItemStack urn, ItemStack upgrade) {
        var list = getUpgradesMutableCopy(urn);

        // First check we don't already have this upgrade
        for (ItemStack itemStack : list) {
            if (itemStack.is(upgrade.getItem())) {
                return false;
            }
        }

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).isEmpty()) {
                list.set(i, upgrade);
                urn.set(LsDataComponents.URN_UPGRADES, ItemContainerContents.fromItems(list));
                return true;
            }
        }

        // No free slots
        return false;
    }

    public static ItemStack selectSwapperUrnToOpen(ServerPlayer player) {
        // Offhand first
        if (isSwapperUrn(player.getOffhandItem())) {
            return player.getOffhandItem();
        }

        // TODO: Curios support?

        // Other items last
        NonNullList<ItemStack> items = player.getInventory().items;
        for (ItemStack stack : items) {
            if (isSwapperUrn(stack)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    private static boolean isSwapperUrn(ItemStack stack) {
        return UrnHelper.isUrn(stack) && UrnHelper.hasUpgrade(stack, LsItems.ITEM_SWAPPER_UPGRADE);
    }

    public static ItemStack selectBackpackUrnToOpen(ServerPlayer player) {
        // Offhand first
        if (isBackpackUrn(player.getOffhandItem())) {
            return player.getOffhandItem();
        }

        // TODO: Curios support?

        // Other items last
        NonNullList<ItemStack> items = player.getInventory().items;
        for (ItemStack stack : items) {
            if (isBackpackUrn(stack)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    private static boolean isBackpackUrn(ItemStack stack) {
        return UrnHelper.isUrn(stack) && UrnHelper.hasUpgrade(stack, LsItems.BACKPACK_UPGRADE);
    }

    public static void loadAllItems(CompoundTag tag, HolderLookup.Provider provider, String tagKey, NonNullList<ItemStack> items) {
        // Taken from ContainerHelper, but can specify the list name
        ListTag listtag = tag.getList(tagKey, 10);

        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < items.size()) {
                items.set(j, ItemStack.parse(provider, compoundtag).orElse(ItemStack.EMPTY));
            }
        }

    }

    public static CompoundTag saveAllItems(CompoundTag tag, HolderLookup.Provider provider, String tagKey, NonNullList<ItemStack> items, boolean alwaysPutTag) {
        // Taken from ContainerHelper, but can specify the list name
        ListTag listtag = new ListTag();

        for (int i = 0; i < items.size(); ++i) {
            ItemStack itemstack = items.get(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte) i);
                listtag.add(itemstack.save(provider, compoundtag));
            }
        }

        if (!listtag.isEmpty() || alwaysPutTag) {
            tag.put(tagKey, listtag);
        }

        return tag;
    }
}
