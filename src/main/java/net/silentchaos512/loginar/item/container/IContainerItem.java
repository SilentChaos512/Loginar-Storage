package net.silentchaos512.loginar.item.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public interface IContainerItem {
    String NBT_INVENTORY = "Inventory";

    int getInventorySize(ItemStack stack);

    boolean canStore(ItemStack stack);

    boolean canPickupItems();

    default boolean canPickup(ItemStack stack) {
        return canPickupItems() && canStore(stack);
    }

    default IItemHandler getInventory(ItemStack stack) {
        ItemStackHandler stackHandler = new ItemStackHandler(getInventorySize(stack));
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound(NBT_INVENTORY));
        return stackHandler;
    }

    default void saveInventory(ItemStack stack, IItemHandler itemHandler, Player player) {
        if (itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put(NBT_INVENTORY, ((ItemStackHandler) itemHandler).serializeNBT());
        }
    }
}
