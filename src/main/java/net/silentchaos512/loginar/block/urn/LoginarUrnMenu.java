package net.silentchaos512.loginar.block.urn;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.setup.UrnTypes;

public class LoginarUrnMenu extends AbstractLoginarUrnMenu {
    private final Container container;
    private final int containerRows;

    public LoginarUrnMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, new SimpleContainer(buf.readByte()), UrnTypes.read(buf));
    }

    public LoginarUrnMenu(int containerId, Inventory playerInventory, Container container, UrnTypes urnType) {
        super(LsMenuTypes.LOGINAR_URN.get(), containerId, urnType);
        this.container = container;
        checkContainerSize(this.container, this.container.getContainerSize());
        int rowSize = urnType().size().width();
        this.containerRows = this.container.getContainerSize() / rowSize;
        int xOffsetPlayerInventory = urnType.renderInfo().playerInventoryXOffset();
        int yOffsetPlayerInventory = (this.containerRows - 4) * 18 + urnType.renderInfo().playerInventoryYOffset();

        addUrnInventorySlots(rowSize, this.containerRows, (slot, x, y) -> new UrnSlot(this.container, slot, x, y));
        addPlayerInventorySlots(playerInventory, xOffsetPlayerInventory, yOffsetPlayerInventory);
    }

    public int getRowCount() {
        return this.containerRows;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    public static class UrnSlot extends Slot {
        public UrnSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return UrnHelper.canUrnStore(stack);
        }
    }
}
