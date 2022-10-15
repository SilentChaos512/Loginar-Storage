package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.silentchaos512.loginar.setup.UrnTypes;
import net.silentchaos512.loginar.util.TextUtil;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class LoginarUrnBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private final UrnTypes type;
    private final UrnData data;
    private NonNullList<ItemStack> itemStacks;
    private int[] slots;

    public LoginarUrnBlockEntity(UrnTypes type, BlockPos pos, BlockState state) {
        super(type.blockEntity().get(), pos, state);
        this.type = type;
        this.data = new UrnData(this.type.inventorySize(), 0x985F45, 0x33EBCB); // FIXME
        this.itemStacks = NonNullList.withSize(this.type.inventorySize(), ItemStack.EMPTY);
        this.slots = IntStream.range(0, this.type.inventorySize()).toArray();
    }

    public int getClayColor() {
        return this.data.clayColor();
    }

    public int getGemColor() {
        return this.data.gemColor();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.itemStacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> list) {
        this.itemStacks = list;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return this.slots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int p_19235_, ItemStack stack, @Nullable Direction direction) {
        return !(Block.byItem(stack.getItem()) instanceof LoginarUrnBlock) && stack.getItem().canFitInsideContainerItems();
    }

    @Override
    public boolean canTakeItemThroughFace(int p_19239_, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtil.translate("container", "loginar_urn");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new LoginarUrnMenu(containerId, playerInventory, this);
    }

    @Override
    public int getContainerSize() {
        return this.data.inventorySize();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag) && tag.contains("Items", 9)) {
            ContainerHelper.loadAllItems(tag, this.itemStacks);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.itemStacks, false);
        }
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new SidedInvWrapper(this, Direction.UP);
    }
}
