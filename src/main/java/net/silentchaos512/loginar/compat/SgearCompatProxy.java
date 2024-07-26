package net.silentchaos512.loginar.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.lib.util.Color;

final class SgearCompatProxy {
    private static final TagKey<Item> BORT_TAG = ItemTags.create(new ResourceLocation("forge", "gems/bort"));
    private SgearCompatProxy() {}

    static int getMainPartColor(ItemStack stack) {
        // Bort exception
        if (stack.is(BORT_TAG)) {
            return 0x96A3D4;
        }

        // Check material color
        /*MaterialInstance material = MaterialInstance.from(stack);
        if (material != null) {
            int color = material.getPrimaryColor(GearType.ALL, PartType.MAIN);
            if ((color & 0xFFFFFF) == 0xFFFFFF) {
                // Try other part types
                for (PartType partType : PartType.getValues()) {
                    int color1 = material.getPrimaryColor(GearType.ALL, partType);
                    if ((color1 & 0xFFFFFF) != 0xFFFFFF) {
                        return color1;
                    }
                }
            }
            return color;
        }*/
        return Color.VALUE_WHITE;
    }
}
