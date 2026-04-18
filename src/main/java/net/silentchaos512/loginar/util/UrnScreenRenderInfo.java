package net.silentchaos512.loginar.util;

public record UrnScreenRenderInfo(
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

    public static final UrnScreenRenderInfo STANDARD = new UrnScreenRenderInfo(
            WIDTH_9X, HEIGHT_X6, true,
            0, 0
    );
    public static final UrnScreenRenderInfo HUGE_9X9 = new UrnScreenRenderInfo(
            WIDTH_9X, HEIGHT_X9, false,
            0, 1
    );
    public static final UrnScreenRenderInfo SUPER_9X12 = new UrnScreenRenderInfo(
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
