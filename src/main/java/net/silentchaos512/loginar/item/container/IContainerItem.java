package net.silentchaos512.loginar.item.container;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.silentchaos512.loginar.setup.LsDataComponents;

public interface IContainerItem {
    int getInventorySize(ItemStack stack);

    boolean canStore(ItemStack stack);

    boolean canPickupItems();

    default boolean canPickup(ItemStack stack) {
        return canPickupItems() && canStore(stack);
    }

    default ComponentItemHandler getInventory(ItemStack stack) {
        return new ComponentItemHandler(stack, LsDataComponents.CONTAINED_ITEMS.get(), getInventorySize(stack));
    }
}
