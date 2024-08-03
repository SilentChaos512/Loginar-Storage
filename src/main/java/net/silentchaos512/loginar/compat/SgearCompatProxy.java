package net.silentchaos512.loginar.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.lib.util.Color;

import java.util.Optional;

final class SgearCompatProxy {
    private static final TagKey<Item> BORT_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "gems/bort"));
    private SgearCompatProxy() {}

    static Optional<Color> getMainPartColor(ItemStack stack) {
        // Bort exception -- TODO: This should be in the main color map
        if (stack.is(BORT_TAG)) {
            return Optional.of(new Color(0x96A3D4));
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
        return Optional.empty();
    }
}
