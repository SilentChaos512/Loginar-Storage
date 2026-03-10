package net.silentchaos512.loginar.item.container;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemAccessItemHandler;
import net.silentchaos512.loginar.setup.LsDataComponents;

public interface IContainerItem {
    int getInventorySize(ItemStack stack);

    boolean canStore(ItemStack stack);

    boolean canPickupItems();

    default boolean canPickup(ItemStack stack) {
        return canPickupItems() && canStore(stack);
    }

    default ItemContainerContents getInventory(ItemStack stack) {
        return stack.getOrDefault(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.EMPTY);
    }

    default ItemAccessItemHandler getItemHandler(ItemStack stack) {
        return new ItemAccessItemHandler(ItemAccess.forStack(stack), LsDataComponents.CONTAINED_ITEMS.get(), getInventorySize(stack));
    }

    default ComponentItemHandler getDeprecatedItemHandler(ItemStack stack) {
        return new ComponentItemHandler(stack, LsDataComponents.CONTAINED_ITEMS.get(), getInventorySize(stack));
    }
}
