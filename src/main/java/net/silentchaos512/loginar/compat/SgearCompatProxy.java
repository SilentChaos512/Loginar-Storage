package net.silentchaos512.loginar.compat;

import net.minecraft.world.item.ItemStack;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.init.ModTags;
import net.silentchaos512.utils.Color;
final class SgearCompatProxy {
    private SgearCompatProxy() {}

    static int getMainPartColor(ItemStack stack) {
        // Bort exception
        if (stack.is(ModTags.Items.GEMS_BORT)) {
            return 0x96A3D4;
        }

        // Check material color
        MaterialInstance material = MaterialInstance.from(stack);
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
        }
        return Color.VALUE_WHITE;
    }
}
