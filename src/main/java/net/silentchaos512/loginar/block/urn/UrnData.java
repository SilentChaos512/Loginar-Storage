package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.setup.UrnTypes;
import net.silentchaos512.utils.EnumUtils;

public class UrnData {
    private final UrnTypes urnType;
    private final int clayColor;
    private final int gemColor;
    private final NonNullList<ItemStack> items;

    public static final String NBT_ROOT = "BlockEntityTag";
    public static final String NBT_CLAY_COLOR = "ClayColor";
    public static final String NBT_GEM_COLOR = "GemColor";
    public static final int DEFAULT_CLAY_COLOR = 0x985F45;
    public static final int DEFAULT_GEM_COLOR = 0x33EBCB;

    public UrnData(UrnTypes urnType, int clayColor, int gemColor) {
        this.urnType = urnType;
        this.clayColor = clayColor;
        this.gemColor = gemColor;
        this.items = NonNullList.withSize(urnType.inventorySize(), ItemStack.EMPTY);
    }

    public static UrnData fromItem(ItemStack stack) {
        if (!UrnHelper.isUrn(stack)) {
            throw new IllegalArgumentException("item is not a loginar urn: " + stack);
        }

        // Obtain all the block data
        LoginarUrnBlock block = (LoginarUrnBlock) ((BlockItem) stack.getItem()).getBlock();
        if (!stack.getOrCreateTag().contains("BlockEntityTag")) {
            return new UrnData(UrnTypes.MEDIUM, DEFAULT_CLAY_COLOR, DEFAULT_GEM_COLOR);
        }
        CompoundTag blockData = stack.getOrCreateTag().getCompound("BlockEntityTag");

        UrnData ret = new UrnData(
                block.getType(),
                blockData.getInt(NBT_CLAY_COLOR),
                blockData.getInt(NBT_GEM_COLOR)
        );
        if (blockData.contains("Items", Tag.TAG_LIST)) {
            ContainerHelper.loadAllItems(blockData, ret.items);
        }
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
}
