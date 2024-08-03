package net.silentchaos512.loginar.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.List;

public class ItemStackUtil {
    public static NonNullList<ItemStack> createMutableCopyOfList(List<ItemStack> list, int size) {
        NonNullList<ItemStack> ret = NonNullList.withSize(size, ItemStack.EMPTY);
        for (int i = 0; i < list.size() && i < size; ++i) {
            ret.set(i, list.get(i));
        }
        return ret;
    }

    public static NonNullList<ItemStack> createMutableCopyOfList(ItemContainerContents contents, int size) {
        NonNullList<ItemStack> ret = NonNullList.withSize(size, ItemStack.EMPTY);
        for (int i = 0; i < contents.getSlots() && i < size; ++i) {
            ret.set(i, contents.getStackInSlot(i));
        }
        return ret;
    }
}
