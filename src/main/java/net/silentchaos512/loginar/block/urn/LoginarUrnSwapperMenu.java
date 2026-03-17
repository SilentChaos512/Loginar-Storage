package net.silentchaos512.loginar.block.urn;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerCopySlot;
import net.neoforged.neoforge.network.PacketDistributor;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.network.SwapItemFromUrnPayload;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsMenuTypes;

public class LoginarUrnSwapperMenu extends AbstractLoginarUrnMenu {
    private final ItemStack item;
    private final int inventorySize;
    private final IItemHandler itemHandler;
    private final int containerRows;

    public LoginarUrnSwapperMenu(int windowId, Inventory inv, RegistryFriendlyByteBuf buf) {
        this(windowId, inv, ItemStack.STREAM_CODEC.decode(buf));
    }

    public LoginarUrnSwapperMenu(int windowId, Inventory inv, ItemStack itemIn) {
        super(LsMenuTypes.LOGINAR_URN_SWAPPER.get(), windowId, getUrnTypeFromItem(itemIn));
        this.item = itemIn;
        this.inventorySize = urnType().totalInventorySize();
        this.itemHandler = new ComponentItemHandler(this.item, LsDataComponents.CONTAINED_ITEMS.get(), this.inventorySize);
        int rowSize = urnType().size().width();
        this.containerRows = this.itemHandler.getSlots() / rowSize;

        // Urn inventory slots
        for (int row = 0; row < containerRows; ++row) {
            for (int col = 0; col < rowSize; ++col) {
                int slot = col + row * rowSize;
                var px = 2 + col * 20;
                var py = 19 + row * 20;
                this.addSlot(new GhostSlot(this.itemHandler, slot, px, py));
            }
        }
    }

    public int getRowCount() {
        return this.containerRows;
    }

    @Override
    public void clicked(int slotIndex, int dragType, ClickType clickType, Player player) {
        if (slotIndex > -1 && slotIndex < this.inventorySize) {
            Slot slot = this.slots.get(slotIndex);
            ItemStack item = slot.getItem();

            LoginarMod.LOGGER.info("Attempting to swap urn item with hand: {}", item);
            PacketDistributor.sendToServer(new SwapItemFromUrnPayload(slotIndex));
            player.closeContainer();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public static class GhostSlot extends ItemHandlerCopySlot {
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
