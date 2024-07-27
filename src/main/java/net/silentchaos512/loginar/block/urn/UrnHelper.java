package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsTags;
import net.silentchaos512.loginar.setup.UrnTypes;

public final class UrnHelper {
    private UrnHelper() {
        throw new IllegalAccessError("Utility class");
    }

    public static boolean isUrn(ItemStack stack) {
        return stack.is(LsTags.Items.URNS);
    }

    public static boolean canUrnStore(ItemStack stack) {
        return !isUrn(stack) && !stack.is(LsTags.Items.URNS_CANNOT_STORE) && stack.getItem().canFitInsideContainerItems();
    }

    public static boolean isUpgrade(ItemStack stack) {
        return stack.is(LsTags.Items.URN_UPGRADES);
    }

    public static boolean hasUpgrade(ItemStack urn, ItemLike upgrade) {
        var data = urn.get(LsDataComponents.URN_DATA);
        if (data == null) return false;

        for (var item : data.upgrades()) {
            if (item.is(upgrade.asItem())) {
                return true;
            }
        }
        return false;
    }

    public static int getUpgradeCount(ItemStack urn) {
        var data = urn.get(LsDataComponents.URN_DATA);
        return data != null ? data.upgrades().size() : 0;
    }

    public static int getMaxUpgradeCount(ItemStack urn) {
        UrnTypes type = UrnTypes.fromItem(urn);
        if (type != null) {
            return type.upgradeSlots();
        }
        return 0;
    }

    public static int getClayColor(ItemStack stack) {
        var data = stack.get(LsDataComponents.URN_DATA);
        return data != null ? data.clayColor() : UrnData.DEFAULT_CLAY_COLOR;
    }

    public static void setClayColor(ItemStack stack, int color) {
        var current = stack.getOrDefault(LsDataComponents.URN_DATA, UrnData.getDefault(stack));
        var newData = new UrnData(current.urnType(), color, current.gemColor(), current.items(), current.upgrades());
        stack.set(LsDataComponents.URN_DATA, newData);
    }

    public static int getGemColor(ItemStack stack) {
        var data = stack.get(LsDataComponents.URN_DATA);
        return data != null ? data.gemColor() : UrnData.DEFAULT_GEM_COLOR;
    }

    public static void setGemColor(ItemStack stack, int color) {
        var current = stack.getOrDefault(LsDataComponents.URN_DATA, UrnData.getDefault(stack));
        var newData = new UrnData(current.urnType(), current.clayColor(), color, current.items(), current.upgrades());
        stack.set(LsDataComponents.URN_DATA, newData);
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

    public static void loadAllItems(CompoundTag tag, HolderLookup.Provider provider, String tagKey, NonNullList<ItemStack> items) {
        // Taken from ContainerHelper, but can specify the list name
        ListTag listtag = tag.getList(tagKey, 10);

        for(int i = 0; i < listtag.size(); ++i) {
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

        for(int i = 0; i < items.size(); ++i) {
            ItemStack itemstack = items.get(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                listtag.add(itemstack.save(provider, compoundtag));
            }
        }

        if (!listtag.isEmpty() || alwaysPutTag) {
            tag.put(tagKey, listtag);
        }

        return tag;
    }
}
