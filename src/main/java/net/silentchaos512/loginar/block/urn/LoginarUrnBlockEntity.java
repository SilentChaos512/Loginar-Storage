package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.silentchaos512.loginar.setup.LsBlockEntityTypes;
import net.silentchaos512.loginar.util.TextUtil;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class LoginarUrnBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private final UrnData data;
    private NonNullList<ItemStack> itemStacks;
    private int[] slots;

    public LoginarUrnBlockEntity(BlockPos pos, BlockState state) {
        super(LsBlockEntityTypes.LOGINAR_URN.get(), pos, state);
        this.data = new UrnData(27, 0x985F45, 0x33EBCB); // FIXME
        this.itemStacks = NonNullList.withSize(data.inventorySize(), ItemStack.EMPTY);
        this.slots = IntStream.range(0, data.inventorySize()).toArray();
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
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return new LoginarUrnMenu(p_58627_, p_58628_, this);
    }

    @Override
    public int getContainerSize() {
        return this.data.inventorySize();
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new SidedInvWrapper(this, Direction.UP);
    }
}