package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.api.TickingUrnUpgrade;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.UrnTypes;
import net.silentchaos512.loginar.util.TextUtil;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class LoginarUrnBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private final UrnTypes type;
    private Color clayColor = UrnHelper.DEFAULT_CLAY_COLOR;
    private Color gemColor = UrnHelper.DEFAULT_GEM_COLOR;
    private NonNullList<ItemStack> items;
    private NonNullList<ItemStack> upgrades;
    private final int[] slots;
    private boolean hasChanged = false;

    public LoginarUrnBlockEntity(UrnTypes type, BlockPos pos, BlockState state) {
        super(type.blockEntity().get(), pos, state);
        this.type = type;
        this.items = NonNullList.withSize(this.type.inventorySize(), ItemStack.EMPTY);
        this.upgrades = NonNullList.withSize(this.type.upgradeSlots(), ItemStack.EMPTY);
        this.slots = IntStream.range(0, this.type.inventorySize()).toArray();
    }

    void setDataFromPlacedItem(ItemStack placedItem) {
        this.clayColor = UrnHelper.getClayColor(placedItem);
        this.gemColor = UrnHelper.getGemColor(placedItem);
        this.items = UrnHelper.getItemsMutableCopy(placedItem);
        this.upgrades = UrnHelper.getUpgradesMutableCopy(placedItem);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder pComponents) {
        super.collectImplicitComponents(pComponents);
        pComponents.set(LsDataComponents.URN_CLAY_COLOR, this.clayColor);
        pComponents.set(LsDataComponents.URN_GEM_COLOR, this.gemColor);
        pComponents.set(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.fromItems(this.items));
        pComponents.set(LsDataComponents.URN_UPGRADES, ItemContainerContents.fromItems(this.upgrades));
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        // Do not drop contents!
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LoginarUrnBlockEntity blockEntity) {
        for (ItemStack upgrade : blockEntity.upgrades) {
            if (!upgrade.isEmpty() && upgrade.getItem() instanceof TickingUrnUpgrade tickingUrnUpgrade) {
                tickingUrnUpgrade.tick(blockEntity, level, pos);
            }
        }

        if (blockEntity.hasChanged) {
            level.sendBlockUpdated(pos, state, state, 3);
            blockEntity.hasChanged = false;
        }
    }

    public Color getClayColor() {
        return this.clayColor;
    }

    public Color getGemColor() {
        return this.gemColor;
    }

    protected NonNullList<ItemStack> getUpgrades() {
        return this.upgrades;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.hasChanged = true;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> list) {
        this.items = list;
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
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.clayColor = new Color(input.getIntOr(UrnHelper.NBT_CLAY_COLOR, UrnHelper.DEFAULT_CLAY_COLOR.getColor()));
        this.gemColor = new Color(input.getIntOr(UrnHelper.NBT_GEM_COLOR, UrnHelper.DEFAULT_GEM_COLOR.getColor()));

        if (!this.tryLoadLootTable(input)) {
            UrnHelper.loadAllItems(input, UrnHelper.NBT_ITEMS, this.items);
        }
        UrnHelper.loadAllItems(input, UrnHelper.NBT_UPGRADES, this.upgrades);

        this.hasChanged = true;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        if (!this.trySaveLootTable(output)) {
            UrnHelper.saveAllItems(output, UrnHelper.NBT_ITEMS, this.items, false);
        }
        UrnHelper.saveAllItems(output, UrnHelper.NBT_UPGRADES, this.upgrades, false);

        output.putInt(UrnHelper.NBT_CLAY_COLOR, this.clayColor.getColor() & 0xFFFFFF);
        output.putInt(UrnHelper.NBT_GEM_COLOR, this.gemColor.getColor() & 0xFFFFFF);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tags = super.getUpdateTag(registries);
        tags.putInt(UrnHelper.NBT_CLAY_COLOR, this.clayColor.getColor() & 0xFFFFFF);
        tags.putInt(UrnHelper.NBT_GEM_COLOR, this.gemColor.getColor() & 0xFFFFFF);
        return tags;
    }

    @Override
    public void onDataPacket(Connection net, ValueInput input) {
        super.onDataPacket(net, input);
        this.clayColor = new Color(input.getIntOr(UrnHelper.NBT_CLAY_COLOR, UrnHelper.DEFAULT_CLAY_COLOR.getColor()));
        this.gemColor = new Color(input.getIntOr(UrnHelper.NBT_GEM_COLOR, UrnHelper.DEFAULT_GEM_COLOR.getColor()));
    }
}
