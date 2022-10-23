package net.silentchaos512.loginar.compat;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.silentchaos512.utils.Color;

public final class SgearCompat {
    private static Boolean modLoaded = null;

    private SgearCompat() {}

    public static boolean isLoaded() {
        if (modLoaded == null) {
            modLoaded = ModList.get().isLoaded("silentgear");
        }
        return modLoaded;
    }

    public static int getMainPartColor(ItemStack stack) {
        if (isLoaded()) {
            return SgearCompatProxy.getMainPartColor(stack);
        }
        return Color.VALUE_WHITE;
    }
}
