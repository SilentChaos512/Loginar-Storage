package net.silentchaos512.loginar.block.urn;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class UrnHelper {
    private UrnHelper() {
        throw new IllegalAccessError("Utility class");
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

    private static CompoundTag getData(ItemStack stack) {
        return stack.getOrCreateTagElement(UrnData.NBT_ROOT);
    }
}
