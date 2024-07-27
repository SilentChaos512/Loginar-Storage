package net.silentchaos512.loginar.item.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.silentchaos512.loginar.setup.LsDataComponents;

public interface IContainerItem {
    int getInventorySize(ItemStack stack);

    boolean canStore(ItemStack stack);

    boolean canPickupItems();

    default boolean canPickup(ItemStack stack) {
        return canPickupItems() && canStore(stack);
    }

    default IItemHandler getInventory(ItemStack stack) {
        return new ItemStackHandler(stack.getOrDefault(LsDataComponents.CONTAINED_ITEMS, NonNullList.withSize(getInventorySize(stack), ItemStack.EMPTY)));
    }

    default void saveInventory(ItemStack stack, IItemHandler itemHandler, Player player) {
        if (itemHandler instanceof ItemStackHandler) {
            NonNullList<ItemStack> list = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);
            for (int i = 0; i < itemHandler.getSlots(); ++i) {
                list.set(i, itemHandler.getStackInSlot(i));
            }
            stack.set(LsDataComponents.CONTAINED_ITEMS, list);
        }
    }
}
