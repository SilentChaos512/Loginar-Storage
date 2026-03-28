package net.silentchaos512.loginar.util;

public record UrnRenderInfo(
        int textureWidth,
        int textureHeight,
        boolean isFlexibleTexture,
        int playerInventoryXOffset,
        int playerInventoryYOffset
) {
    private static final int WIDTH_9X = 176;
    private static final int WIDTH_12X = 236;
    private static final int HEIGHT_X6 = 222;
    private static final int HEIGHT_X9 = 276;

    public static final UrnRenderInfo STANDARD = new UrnRenderInfo(
            WIDTH_9X, HEIGHT_X6, true,
            0, 0
    );
    public static final UrnRenderInfo HUGE_9X9 = new UrnRenderInfo(
            WIDTH_9X, HEIGHT_X9, false,
            0, 1
    );
    public static final UrnRenderInfo SUPER_9X12 = new UrnRenderInfo(
            WIDTH_12X, HEIGHT_X9, false,
            31, 1
    );

    public int textureHeight(int containerRows) {
        if (this.isFlexibleTexture) {
            return 114 + containerRows * 18;
        }
        return this.textureHeight;
    }
}
