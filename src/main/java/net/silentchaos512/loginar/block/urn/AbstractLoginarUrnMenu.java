package net.silentchaos512.loginar.block.urn;

import com.mojang.datafixers.util.Function3;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.setup.UrnTypes;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractLoginarUrnMenu extends AbstractContainerMenu {
    protected final UrnTypes urnType;

    protected AbstractLoginarUrnMenu(@Nullable MenuType<?> menuType, int containerId, UrnTypes urnType) {
        super(menuType, containerId);
        this.urnType = urnType;
    }

    public UrnTypes urnType() {
        return this.urnType;
    }

    public static UrnTypes getUrnTypeFromBlockEntity(Container container) {
        if (!(container instanceof LoginarUrnBlockEntity urnBlockEntity)) {
            throw new IllegalArgumentException("Not a loginar urn: " + container);
        }
        return urnBlockEntity.getUrnType();
    }

    public static UrnTypes getUrnTypeFromItem(ItemStack stack) {
        if (!(stack.getItem() instanceof LoginarUrnBlockItem urnBlockItem)) {
            throw new IllegalArgumentException("Not a loginar urn: " + stack);
        }
        return urnBlockItem.getUrnType();
    }

    protected void addUrnInventorySlots(int rowSize, int rowCount, Function3<Integer, Integer, Integer, ? extends Slot> slotConstructor) {
        final int xOffset = rowSize == 12 ? 11 : 8;
        final int yOffset = 18;
        for(int row = 0; row < rowCount; ++row) {
            for(int col = 0; col < rowSize; ++col) {
                var slot = col + row * rowSize;
                var px = xOffset + col * 18;
                var py = yOffset + row * 18;
                this.addSlot(slotConstructor.apply(slot, px, py));
            }
        }
    }

    protected void addPlayerInventorySlots(Inventory playerInventory, int xOffset, int yOffset) {
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                var slot = col + row * 9 + 9;
                var px = 8 + col * 18 + xOffset;
                var py = 103 + row * 18 + yOffset;
                this.addSlot(new Slot(playerInventory, slot, px, py));
            }
        }

        for(int slot = 0; slot < 9; ++slot) {
            var px = 8 + slot * 18 + xOffset;
            var py = 161 + yOffset;
            this.addSlot(new Slot(playerInventory, slot, px, py));
        }
    }
}
