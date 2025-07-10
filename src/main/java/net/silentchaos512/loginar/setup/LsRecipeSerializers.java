package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.crafting.recipe.UrnBaseRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnModificationRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnUpgradeRecipe;

import java.util.function.Supplier;

public class LsRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, LoginarMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UrnBaseRecipe>> URN = register(
            "urn_base",
            UrnBaseRecipe.Serializer::new
    );
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UrnModificationRecipe>> URN_MODIFICATION = register(
            "urn_modification",
            () -> new CustomRecipe.Serializer<>(UrnModificationRecipe::new)
    );
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UrnUpgradeRecipe>> URN_UPGRADE = register(
            "urn_upgrade",
            UrnUpgradeRecipe.Serializer::new
    );

    private static <T extends RecipeSerializer<?>> DeferredHolder<RecipeSerializer<?>, T> register(String name, Supplier<T> serializer) {
        return REGISTER.register(name, serializer);
    }
}
