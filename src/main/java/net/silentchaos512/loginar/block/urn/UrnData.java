package net.silentchaos512.loginar.block.urn;

public record UrnData(int inventorySize, int clayColor, int gemColor) {
    public static final String NBT_ROOT = "BlockEntityTag";
    public static final String NBT_CLAY_COLOR = "ClayColor";
    public static final String NBT_GEM_COLOR = "GemColor";
    public static final int DEFAULT_CLAY_COLOR = 0x985F45;
    public static final int DEFAULT_GEM_COLOR = 0x33EBCB;
}
