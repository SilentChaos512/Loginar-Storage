package net.silentchaos512.loginar.block.urn;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.setup.LsTags;

public class LoginarUrnBackpackMenu extends AbstractContainerMenu {
    private final ItemStack item;
    private final UrnData urnData;
    private final IItemHandler itemHandler;
    private final int containerRows;
    int urnSlot = -1;

    public LoginarUrnBackpackMenu(int windowId, Inventory inv, FriendlyByteBuf data) {
        this(windowId, inv, data.readItem());
    }

    public LoginarUrnBackpackMenu(int windowId, Inventory inv, ItemStack itemIn) {
        super(LsMenuTypes.LOGINAR_URN_BACKPACK.get(), windowId);
        this.item = itemIn;
        this.urnData = UrnData.fromItem(this.item);
        this.itemHandler = new ItemStackHandler(this.urnData.items());
        this.containerRows = this.itemHandler.getSlots() / 9;
        int i = (containerRows - 4) * 18;

        // Urn inventory slots
        for(int j = 0; j < containerRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new BackpackSlot(this.itemHandler, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        // Player inventory slots
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        // Player hotbar slots
        for(int i1 = 0; i1 < 9; ++i1) {
            Slot slot = this.addSlot(new Slot(inv, i1, 8 + i1 * 18, 161 + i));
            if (i1 == inv.selected && ItemStack.matches(inv.getSelected(), this.item)) {
                this.urnSlot = slot.index;
            }
        }
    }

    public int getRowCount() {return this.containerRows; }

    @Override
    public void removed(Player player) {
        super.removed(player);
        // FIXME: Does not save changes
        CompoundTag itemHandlerNbt = ((ItemStackHandler) this.itemHandler).serializeNBT();
        this.item.getOrCreateTag().getCompound("BlockEntityTag").put("Items", itemHandlerNbt.getList("Items", Tag.TAG_COMPOUND));
    }

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
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId < 0 || slotId > slots.size()) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            return;
        }

        Slot slot = slots.get(slotId);
        if (!canTake(slotId, slot, dragType, player, clickTypeIn)) {
            return;
        }

        super.clicked(slotId, dragType, clickTypeIn, player);
    }

    public boolean canTake(int slotId, Slot slot, int button, Player player, ClickType clickType) {
        if (slotId == urnSlot) {
            return false;
        }

        // Hotbar swapping via number keys
        if (clickType == ClickType.SWAP) {
            int hotbarId = itemHandler.getSlots() + 27 + button;
            // Block swapping with container
            if (urnSlot == hotbarId) {
                return false;
            }

            Slot hotbarSlot = getSlot(hotbarId);
            if (slotId <= itemHandler.getSlots() - 1) {
                return !UrnHelper.isUrn(slot.getItem()) && !UrnHelper.isUrn(hotbarSlot.getItem());
            }
        }

        return true;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public static class BackpackSlot extends SlotItemHandler {
        public BackpackSlot(IItemHandler container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return !stack.is(LsTags.Items.URNS);
        }
    }
}
