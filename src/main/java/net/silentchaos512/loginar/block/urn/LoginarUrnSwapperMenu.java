package net.silentchaos512.loginar.block.urn;

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
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.network.HandleUrnItemSwapPacket;
import net.silentchaos512.loginar.network.LsNetwork;
import net.silentchaos512.loginar.setup.LsMenuTypes;

public class LoginarUrnSwapperMenu extends AbstractContainerMenu {
    private final ItemStack item;
    private final UrnData urnData;
    private final IItemHandler itemHandler;
    private final int containerRows;

    public LoginarUrnSwapperMenu(int windowId, Inventory inv, FriendlyByteBuf data) {
        this(windowId, inv, data.readItem());
    }

    public LoginarUrnSwapperMenu(int windowId, Inventory inv, ItemStack itemIn) {
        super(LsMenuTypes.LOGINAR_URN_SWAPPER.get(), windowId);
        this.item = itemIn;
        this.urnData = UrnData.fromItem(this.item);
        this.itemHandler = new ItemStackHandler(this.urnData.items());
        this.containerRows = this.itemHandler.getSlots() / 9;

        // Urn inventory slots
        for (int j = 0; j < containerRows; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new GhostSlot(this.itemHandler, k + j * 9, 2 + k * 20, 2 + j * 20));
            }
        }
    }

    public int getRowCount() {
        return this.containerRows;
    }

    @Override
    public void clicked(int slotIndex, int dragType, ClickType clickType, Player player) {
        if (slotIndex > -1 && slotIndex < this.urnData.urnType().inventorySize()) {
            Slot slot = this.slots.get(slotIndex);
            ItemStack item = slot.getItem();

            LoginarMod.LOGGER.info("Attempting to swap urn item with hand: {}", item);
            LsNetwork.channel.sendToServer(new HandleUrnItemSwapPacket(slotIndex));
            player.closeContainer();
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        // FIXME: Does not save changes
        /*CompoundTag itemHandlerNbt = ((ItemStackHandler) this.itemHandler).serializeNBT();
        this.item.getOrCreateTag().getCompound("BlockEntityTag").put("Items", itemHandlerNbt.getList("Items", Tag.TAG_COMPOUND));*/
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public static class GhostSlot extends SlotItemHandler {
        public GhostSlot(IItemHandler container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPickup(Player player) {
            return false;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
}
