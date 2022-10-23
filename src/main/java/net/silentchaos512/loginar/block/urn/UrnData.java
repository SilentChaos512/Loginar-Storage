package net.silentchaos512.loginar.block.urn;

import net.minecraft.nbt.CompoundTag;

public record UrnData(int inventorySize, int clayColor, int gemColor) {
    public static final String NBT_ROOT = "BlockEntityTag";
    public static final String NBT_CLAY_COLOR = "ClayColor";
    public static final String NBT_GEM_COLOR = "GemColor";
    public static final int DEFAULT_CLAY_COLOR = 0x985F45;
    public static final int DEFAULT_GEM_COLOR = 0x33EBCB;

    public static UrnData readNbt(CompoundTag tags) {
        return new UrnData(tags.getInt("Size"), tags.getInt(NBT_CLAY_COLOR), tags.getInt(NBT_GEM_COLOR));
    }

    public void writeNbt(CompoundTag tags) {
        tags.putInt("Size", inventorySize);
        tags.putInt(NBT_CLAY_COLOR, clayColor);
        tags.putInt(NBT_GEM_COLOR, gemColor);
    }
}
