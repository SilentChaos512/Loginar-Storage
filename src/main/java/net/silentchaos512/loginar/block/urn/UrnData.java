package net.silentchaos512.loginar.block.urn;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.lib.util.EnumUtils;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.UrnTypes;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record UrnData(
        UrnTypes urnType,
        int clayColor,
        int gemColor,
        List<ItemStack> items,
        List<ItemStack> upgrades
) {
    public static final Codec<UrnData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    UrnTypes.CODEC.fieldOf("type").forGetter(d -> d.urnType),
                    Codec.INT.fieldOf("clay_color").forGetter(d -> d.clayColor),
                    Codec.INT.fieldOf("gem_color").forGetter(d -> d.gemColor),
                    Codec.list(ItemStack.OPTIONAL_CODEC).fieldOf("items").forGetter(d -> d.items),
                    Codec.list(ItemStack.OPTIONAL_CODEC).fieldOf("upgrades").forGetter(d -> d.upgrades)
            ).apply(instance, UrnData::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, UrnData> STREAM_CODEC = StreamCodec.composite(
            UrnTypes.STREAM_CODEC, d -> d.urnType,
            ByteBufCodecs.INT, d -> d.clayColor,
            ByteBufCodecs.INT, d -> d.gemColor,
            ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()), d -> d.items,
            ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()), d -> d.upgrades,
            UrnData::new
    );

    public static final String NBT_ROOT = "BlockEntityTag";
    public static final String NBT_CLAY_COLOR = "ClayColor";
    public static final String NBT_GEM_COLOR = "GemColor";
    public static final String NBT_ITEMS = "Items";
    public static final String NBT_UPGRADES = "Upgrades";

    public static final int DEFAULT_CLAY_COLOR = 0x985F45;
    public static final int DEFAULT_GEM_COLOR = 0x33EBCB;

    public UrnData(UrnTypes urnType, int clayColor, int gemColor) {
        this(urnType, clayColor, gemColor, Collections.emptyList(), Collections.emptyList());
    }

    public UrnData(UrnTypes urnType, int clayColor, int gemColor, List<ItemStack> items, List<ItemStack> upgrades) {
        this.urnType = urnType;
        this.clayColor = clayColor;
        this.gemColor = gemColor;
        this.items = ImmutableList.copyOf(items);
        this.upgrades = ImmutableList.copyOf(upgrades);
        LoginarMod.LOGGER.info(this.upgrades);
    }

    public NonNullList<ItemStack> copyItems() {
        return createMutableCopyOfList(this.items, this.urnType.inventorySize());
    }

    public NonNullList<ItemStack> copyUpgrades() {
        return createMutableCopyOfList(this.upgrades, this.urnType.upgradeSlots());
    }

    public NonNullList<ItemStack> createMutableCopyOfList(List<ItemStack> list, int size) {
        NonNullList<ItemStack> ret = NonNullList.withSize(size, ItemStack.EMPTY);
        for (int i = 0; i < list.size() && i < size; ++i) {
            ret.set(i, list.get(i));
        }
        return ret;
    }

    public static UrnData getDefault(ItemStack stack) {
        return getDefault(Objects.requireNonNull(UrnTypes.fromItem(stack)));
    }

    public static UrnData getDefault(UrnTypes type) {
        return new UrnData(type, DEFAULT_CLAY_COLOR, DEFAULT_GEM_COLOR);
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
        var ret = getDefault(stack);
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

    public UrnData withItems(List<ItemStack> list) {
        return new UrnData(
                this.urnType,
                this.clayColor,
                this.gemColor,
                list,
                this.upgrades
        );
    }

    public UrnData withUpgrades(List<ItemStack> list) {
        return new UrnData(
                this.urnType,
                this.clayColor,
                this.gemColor,
                this.items,
                list
        );
    }

    @Nullable
    public UrnData withNewUpgrade(ItemStack stack) {
        List<ItemStack> list = this.copyUpgrades();

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).isEmpty()) {
                list.set(i, stack);
                return new UrnData(
                        this.urnType,
                        this.clayColor,
                        this.gemColor,
                        this.items,
                        list
                );
            }
        }

        return null;
    }

    @Nullable
    public UrnData tryAddItem(ItemStack stack) {
        if (!UrnHelper.canUrnStore(stack)) {
            return null;
        }

        List<ItemStack> list = this.copyItems();

        for (int slot = 0; slot < list.size(); ++slot) {
            ItemStack stackInSlot = list.get(slot);
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

                list.set(slot, stackInSlot);
            } else {
                list.set(slot, stack.copy());
                stack.setCount(0);
            }

            return withItems(list);
        }

        return null;
    }
}
