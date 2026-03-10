package net.silentchaos512.loginar.compat;

import net.minecraft.world.item.ItemStack;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.setup.SgRegistries;
import net.silentchaos512.gear.setup.gear.GearTypes;
import net.silentchaos512.gear.setup.gear.PartTypes;
import net.silentchaos512.lib.util.Color;

import java.util.Optional;

final class SgearCompatProxy {
    private SgearCompatProxy() {}

    static Optional<Color> getMainPartColor(ItemStack stack) {
        // Check material color
        MaterialInstance material = MaterialInstance.from(stack);
        if (material != null) {
            int color = material.getColor(GearTypes.ALL.get(), PartTypes.MAIN.get());
            if ((color & 0xFFFFFF) == 0xFFFFFF) {
                // Try other part types
                for (PartType partType : SgRegistries.PART_TYPE) {
                    int color1 = material.getColor(GearTypes.ALL.get(), partType);
                    if ((color1 & 0xFFFFFF) != 0xFFFFFF) {
                        return Optional.of(new Color(color1));
                    }
                }
            }
            return Optional.of(new Color(color));
        }
        return Optional.empty();
    }
}
