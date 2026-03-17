package net.silentchaos512.loginar.util;

public record UrnSize(
        int width,
        int height,
        int pages,
        int upgradeSlots
) {
    public UrnSize(int width, int height, int pages, int upgradeSlots) {
        if (width < 1) {
            throw new IllegalArgumentException("width must be greater than zero");
        }
        if (width > 12) {
            throw new IllegalArgumentException("width must be less than 13");
        }
        this.width = width;
        if (height < 1) {
            throw new IllegalArgumentException("height must be greater than zero");
        }
        if (height > 9) {
            throw new IllegalArgumentException("height must be less than 10");
        }
        this.height = height;
        if (pages < 1) {
            throw new IllegalArgumentException("pages must be greater than zero");
        }
        if (pages > 4) {
            throw new IllegalArgumentException("pages must be less than 5");
        }
        this.pages = pages;
        this.upgradeSlots = upgradeSlots;
    }

    public int getInventorySize() {
        return width * height * pages;
    }
}
