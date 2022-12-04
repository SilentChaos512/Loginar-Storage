package net.silentchaos512.loginar.block.urn;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.setup.LsTags;

public class LoginarUrnMenu extends AbstractContainerMenu {
    private final Container container;
    private final int containerRows;

    public LoginarUrnMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, new SimpleContainer(buf.readByte()));
    }

    public LoginarUrnMenu(int containerId, Inventory playerInventory, Container container) {
        super(LsMenuTypes.LOGINAR_URN.get(), containerId);
        this.container = container;
        checkContainerSize(this.container, this.container.getContainerSize());
        this.containerRows = this.container.getContainerSize() / 9;
//        this.container.startOpen(playerInventory.player);
        int i = (containerRows - 4) * 18;

        // Urn inventory slots
        for(int j = 0; j < containerRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new UrnSlot(this.container, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        // Player inventory slots
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        // Player hotbar slots
        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }
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
            return !stack.is(LsTags.Items.URNS);
        }
    }
}
