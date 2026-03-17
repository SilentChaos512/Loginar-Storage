package net.silentchaos512.loginar.util;

public record UrnRenderInfo(
        int playerInventoryXOffset,
        int playerInventoryYOffset
) {
    public static final UrnRenderInfo STANDARD = new UrnRenderInfo(0, 0);
    public static final UrnRenderInfo HUGE_9X9 = new UrnRenderInfo(0, 1);
    public static final UrnRenderInfo SUPER_9X12 = new UrnRenderInfo(31, 1);
}
