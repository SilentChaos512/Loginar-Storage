package net.silentchaos512.loginar.setup;

import net.minecraft.world.food.FoodProperties;

public class LsFoods {
    public static final FoodProperties LOGINAR_TENTACLE = (new FoodProperties.Builder())
            .nutrition(2)
            .saturationModifier(0.3f)
            .build();

    public static final FoodProperties LOGINAR_CALAMARI = (new FoodProperties.Builder())
            .nutrition(12)
            .saturationModifier(1.2f)
            .build();
}
