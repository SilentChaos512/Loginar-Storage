package net.silentchaos512.loginar.block.urn;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerCopySlot;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsMenuTypes;

public class LoginarUrnBackpackMenu extends AbstractLoginarUrnMenu {
    private final ItemStack item;
    private final IItemHandler itemHandler;
    private final int containerRows;
    int urnSlot = -1;

    public LoginarUrnBackpackMenu(int windowId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        this(windowId, playerInventory, ItemStack.STREAM_CODEC.decode(data));
    }

    public LoginarUrnBackpackMenu(int windowId, Inventory playerInventory, ItemStack itemIn) {
        super(LsMenuTypes.LOGINAR_URN_BACKPACK.get(), windowId, getUrnTypeFromItem(itemIn));
        this.item = itemIn;
        var size = urnType().totalInventorySize();
        var rowSize = urnType().size().width();
        this.itemHandler = new ComponentItemHandler(this.item, LsDataComponents.CONTAINED_ITEMS.get(), size);
        this.containerRows = this.itemHandler.getSlots() / rowSize;
        int xOffsetPlayerInventory = urnType().renderInfo().playerInventoryXOffset();
        int yOffsetPlayerInventory = (containerRows - 4) * 18 + urnType().renderInfo().playerInventoryYOffset();

        addUrnInventorySlots(rowSize, this.containerRows, (slot, x, y) -> new BackpackSlot(this.itemHandler, slot, x, y));
        addPlayerInventorySlots(playerInventory, xOffsetPlayerInventory, yOffsetPlayerInventory);
    }

    public int getRowCount() {return this.containerRows; }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);

        //noinspection ConstantConditions
        if (index == urnSlot || slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        if (!slot.mayPickup(player)) {
            return slot.getItem();
        }

        ItemStack itemstack;
        ItemStack itemstack1 = slot.getItem();
        itemstack = itemstack1.copy();
        int inventorySize = this.itemHandler.getSlots();

        if (index < inventorySize) {
            if (!this.moveItemStackTo(itemstack1, inventorySize, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(itemstack1, 0, inventorySize, false)) {
            return ItemStack.EMPTY;
        }

        if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return itemstack;
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, ContainerInput containerInput, Player player) {
        if (slotIndex < 0 || slotIndex > slots.size()) {
            super.clicked(slotIndex, buttonNum, containerInput, player);
            return;
        }

        Slot slot = slots.get(slotIndex);
        if (!canTake(slotIndex, slot, buttonNum, player, containerInput)) {
            return;
        }

        super.clicked(slotIndex, buttonNum, containerInput, player);
    }

    public boolean canTake(int slotId, Slot slot, int button, Player player, ContainerInput clickType) {
        if (slotId == urnSlot) {
            return false;
        }

        // Hotbar swapping via number keys
        if (clickType == ContainerInput.SWAP) {
            int hotbarId = itemHandler.getSlots() + 27 + button;
            // Block swapping with container
            if (urnSlot == hotbarId) {
                return false;
            }

            Slot hotbarSlot = getSlot(hotbarId);
            if (slotId <= itemHandler.getSlots() - 1) {
                return UrnHelper.canUrnStore(slot.getItem()) && UrnHelper.canUrnStore(hotbarSlot.getItem());
            }
        }

        return true;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public static class BackpackSlot extends ItemHandlerCopySlot {
        public BackpackSlot(IItemHandler container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return UrnHelper.canUrnStore(stack);
        }
    }
}
