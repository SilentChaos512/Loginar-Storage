package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapedRecipe;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.crafting.recipe.UrnBaseRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnModifcationRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnUpgradeRecipe;

import java.util.function.Supplier;

public class LsRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LoginarMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> URN_BASE = register("urn_base",
            UrnBaseRecipe.Serializer::new
    );
    public static final RegistryObject<SimpleRecipeSerializer<?>> URN_MODIFICATION = register("urn_modification",
            () -> new SimpleRecipeSerializer<>(UrnModifcationRecipe::new)
    );
    public static final RegistryObject<RecipeSerializer<?>> URN_UPGRADE = register("urn_upgrade",
            () -> ExtendedShapedRecipe.Serializer.basic(UrnUpgradeRecipe::new)
    );

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(String name, Supplier<T> serializer) {
        return REGISTER.register(name, serializer);
    }
}
