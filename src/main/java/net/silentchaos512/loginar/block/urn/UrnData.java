package net.silentchaos512.loginar.block.urn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.lib.util.EnumUtils;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.UrnTypes;

import java.util.List;
import java.util.Objects;

public class UrnData {
    public static final Codec<UrnData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    UrnTypes.CODEC.fieldOf("type").forGetter(d -> d.urnType),
                    Codec.INT.fieldOf("clay_color").forGetter(d -> d.clayColor),
                    Codec.INT.fieldOf("gem_color").forGetter(d -> d.gemColor),
                    Codec.list(ItemStack.CODEC).fieldOf("items").forGetter(d -> d.items),
                    Codec.list(ItemStack.CODEC).fieldOf("upgrades").forGetter(d -> d.upgrades)
            ).apply(instance, UrnData::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, UrnData> STREAM_CODEC = StreamCodec.composite(
            UrnTypes.STREAM_CODEC, d -> d.urnType,
            ByteBufCodecs.INT, d -> d.clayColor,
            ByteBufCodecs.INT, d -> d.gemColor,
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()), d -> d.items,
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()), d -> d.upgrades,
            UrnData::new
    );

    private final UrnTypes urnType;
    private final int clayColor;
    private final int gemColor;
    private NonNullList<ItemStack> items;
    private NonNullList<ItemStack> upgrades;

    public static final String NBT_ROOT = "BlockEntityTag";
    public static final String NBT_CLAY_COLOR = "ClayColor";
    public static final String NBT_GEM_COLOR = "GemColor";
    public static final String NBT_ITEMS = "Items";
    public static final String NBT_UPGRADES = "Upgrades";

    public static final int DEFAULT_CLAY_COLOR = 0x985F45;
    public static final int DEFAULT_GEM_COLOR = 0x33EBCB;

    public UrnData(UrnTypes urnType, int clayColor, int gemColor) {
        this.urnType = urnType;
        this.clayColor = clayColor;
        this.gemColor = gemColor;
        this.items = NonNullList.withSize(urnType.inventorySize(), ItemStack.EMPTY);
        this.upgrades = NonNullList.withSize(urnType.upgradeSlots(), ItemStack.EMPTY);
    }

    public UrnData(UrnTypes urnType, int clayColor, int gemColor, List<ItemStack> items, List<ItemStack> upgrades) {
        this(urnType, clayColor, gemColor);
        for (int i = 0; i < items.size() && i < this.items.size(); ++i) {
            this.items.set(i, items.get(i));
        }
        for (int i = 0; i < upgrades.size() && i < this.upgrades.size(); ++i) {
            this.upgrades.set(i, upgrades.get(i));
        }
    }

    public static UrnData getDefault(ItemStack stack) {
        return new UrnData(Objects.requireNonNull(UrnTypes.fromItem(stack)), DEFAULT_CLAY_COLOR, DEFAULT_GEM_COLOR);
    }

    public static UrnData fromItem(ItemStack stack) {
        if (!UrnHelper.isUrn(stack)) {
            throw new IllegalArgumentException("item is not a loginar urn: " + stack);
        }

        // Get data from item data component
        var urnData = stack.get(LsDataComponents.URN_DATA);
        if (urnData != null) {
            return urnData;
        }

        // Missing data, build a new default object and save it
        LoginarUrnBlock block = (LoginarUrnBlock) ((BlockItem) stack.getItem()).getBlock();
        var ret = new UrnData(
                block.getType(),
                DEFAULT_CLAY_COLOR,
                DEFAULT_GEM_COLOR
        );
        stack.set(LsDataComponents.URN_DATA, ret);
        return ret;
    }

    public static UrnData readNbt(CompoundTag tags) {
        return new UrnData(
                EnumUtils.byOrdinal(tags.getInt("Type"), UrnTypes.MEDIUM),
                tags.getInt(NBT_CLAY_COLOR),
                tags.getInt(NBT_GEM_COLOR)
        );
    }

    public void writeNbt(CompoundTag tags) {
        tags.putInt("Type", urnType.ordinal());
        tags.putInt(NBT_CLAY_COLOR, clayColor);
        tags.putInt(NBT_GEM_COLOR, gemColor);
    }

    public UrnTypes urnType() {
        return urnType;
    }

    public int clayColor() {
        return clayColor;
    }

    public int gemColor() {
        return gemColor;
    }

    public NonNullList<ItemStack> items() {
        return items;
    }

    public void setItems(NonNullList<ItemStack> list) {
        this.items = list;
    }

    public NonNullList<ItemStack> upgrades() {
        return upgrades;
    }

    public void addUpgrade(ItemStack stack) {
        for (int i = 0; i < upgrades.size(); ++i) {
            if (upgrades.get(i).isEmpty()) {
                upgrades.set(i, stack);
                return;
            }
        }
    }

    public boolean tryAddItemToInventory(ItemStack stack) {
        if (!UrnHelper.canUrnStore(stack)) {
            return false;
        }

        for (int slot = 0; slot < this.items.size(); ++slot) {
            ItemStack stackInSlot = this.items.get(slot);
            if (!stackInSlot.isEmpty() && !ItemStack.isSameItemSameComponents(stack, stackInSlot)) {
                continue;
            }

            if (!stackInSlot.isEmpty()) {
                int amountCanFit = Math.min(stack.getCount(), stackInSlot.getMaxStackSize() - stackInSlot.getCount());
                if (amountCanFit <= 0) {
                    continue;
                }
                stackInSlot.setCount(stackInSlot.getCount() + amountCanFit);
                stack.setCount(stack.getCount() - amountCanFit);

                this.items.set(slot, stackInSlot);
            } else {
                this.items.set(slot, stack.copy());
                stack.setCount(0);
            }

            return true;
        }

        return false;
    }
}
