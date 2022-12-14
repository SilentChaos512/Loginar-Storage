package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
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
    private UrnData data;
    private final int[] slots;
    private boolean hasChanged = false;

    public LoginarUrnBlockEntity(UrnTypes type, BlockPos pos, BlockState state) {
        super(type.blockEntity().get(), pos, state);
        this.type = type;
        this.data = new UrnData(this.type, UrnData.DEFAULT_CLAY_COLOR, UrnData.DEFAULT_GEM_COLOR);
        this.slots = IntStream.range(0, this.type.inventorySize()).toArray();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LoginarUrnBlockEntity blockEntity) {
        if (blockEntity.hasChanged) {
            level.sendBlockUpdated(pos, state, state, 3);
            blockEntity.hasChanged = false;
        }
    }

    public int getClayColor() {
        return this.data.clayColor();
    }

    public int getGemColor() {
        return this.data.gemColor();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.data.items();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> list) {
        this.data.setItems(list);
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
        return this.type.inventorySize();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        int clayColor = UrnData.DEFAULT_CLAY_COLOR;
        if (tag.contains(UrnData.NBT_CLAY_COLOR)) {
            clayColor = tag.getInt(UrnData.NBT_CLAY_COLOR);
        }

        int gemColor = UrnData.DEFAULT_GEM_COLOR;
        if (tag.contains(UrnData.NBT_GEM_COLOR)) {
            gemColor = tag.getInt(UrnData.NBT_GEM_COLOR);
        }

        this.data = new UrnData(this.type, clayColor, gemColor);

        if (!this.tryLoadLootTable(tag) && tag.contains("Items", Tag.TAG_LIST)) {
            UrnHelper.loadAllItems(tag, "Items", this.data.items());
        }

        if (tag.contains("Upgrades", Tag.TAG_LIST)) {
            UrnHelper.loadAllItems(tag, "Upgrades", this.data.upgrades());
        }

        this.hasChanged = true;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            UrnHelper.saveAllItems(tag, "Items", this.data.items(), false);
        }
        UrnHelper.saveAllItems(tag, "Upgrades", this.data.upgrades(), false);

        tag.putInt(UrnData.NBT_CLAY_COLOR, this.data.clayColor());
        tag.putInt(UrnData.NBT_GEM_COLOR, this.data.gemColor());
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tags = super.getUpdateTag();
        this.data.writeNbt(tags);
        return tags;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        CompoundTag tags = pkt.getTag();
        if (tags != null) {
            this.data = UrnData.readNbt(tags);
        }
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new SidedInvWrapper(this, Direction.UP);
    }
}
