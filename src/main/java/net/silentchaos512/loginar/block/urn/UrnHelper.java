package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
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

    public static boolean isSwappableItem(ItemStack stack) {
        // TODO: Blacklist for other items, like shulker boxes
        return !isUrn(stack);
    }

    public static boolean isUpgrade(ItemStack stack) {
        return stack.is(LsTags.Items.URN_UPGRADES);
    }

    public static boolean hasUpgrade(ItemStack urn, ItemLike upgrade) {
        // Efficiently check for a matching item ID
        ResourceLocation upgradeId = BuiltInRegistries.ITEM.getKey(upgrade.asItem());
        CompoundTag tags = getData(urn);
        ListTag listTag = tags.getList("Upgrades", Tag.TAG_COMPOUND);

        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            ResourceLocation id = ResourceLocation.tryParse(compoundTag.getString("id"));
            if (id != null && id.equals(upgradeId)) {
                return true;
            }
        }

        return false;
    }

    public static int getUpgradeCount(ItemStack urn) {
        CompoundTag tags = getData(urn);
        if (!tags.contains("Upgrades")) {
            return 0;
        }
        return tags.getList("Upgrades", Tag.TAG_COMPOUND).size();
    }

    public static int getMaxUpgradeCount(ItemStack urn) {
        UrnTypes type = UrnTypes.fromItem(urn);
        if (type != null) {
            return type.upgradeSlots();
        }
        return 0;
    }

    public static int getClayColor(ItemStack stack) {
        CompoundTag tags = getData(stack);
        return tags.contains(UrnData.NBT_CLAY_COLOR) ? tags.getInt(UrnData.NBT_CLAY_COLOR) : UrnData.DEFAULT_CLAY_COLOR;
    }

    public static void setClayColor(ItemStack stack, int color) {
        getData(stack).putInt(UrnData.NBT_CLAY_COLOR, color);
    }

    public static int getGemColor(ItemStack stack) {
        CompoundTag tags = getData(stack);
        return tags.contains(UrnData.NBT_GEM_COLOR) ? tags.getInt(UrnData.NBT_GEM_COLOR) : UrnData.DEFAULT_GEM_COLOR;
    }

    public static void setGemColor(ItemStack stack, int color) {
        getData(stack).putInt(UrnData.NBT_GEM_COLOR, color);
    }

    static boolean canUrnStoreItem(ItemStack stack) {
        return !stack.is(LsTags.Items.URNS);
    }

    public static CompoundTag getData(ItemStack stack) {
        return stack.getOrCreateTagElement(UrnData.NBT_ROOT);
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

    public static void loadAllItems(CompoundTag tag, String tagKey, NonNullList<ItemStack> items) {
        // Taken from ContainerHelper, but can specify the list name
        ListTag listtag = tag.getList(tagKey, 10);

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < items.size()) {
                items.set(j, ItemStack.of(compoundtag));
            }
        }

    }

    public static CompoundTag saveAllItems(CompoundTag tag, String tagKey, NonNullList<ItemStack> items, boolean p_18979_) {
        // Taken from ContainerHelper, but can specify the list name
        ListTag listtag = new ListTag();

        for(int i = 0; i < items.size(); ++i) {
            ItemStack itemstack = items.get(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }
        }

        if (!listtag.isEmpty() || p_18979_) {
            tag.put(tagKey, listtag);
        }

        return tag;
    }
}
