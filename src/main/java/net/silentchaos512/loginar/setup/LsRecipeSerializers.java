package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapedRecipe;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.crafting.recipe.UrnModificationRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnBaseRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnUpgradeRecipe;

import java.util.function.Supplier;

public class LsRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, LoginarMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> URN = register("urn_base",
            UrnBaseRecipe.Serializer::new
    );
    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<?>> URN_MODIFICATION = register("urn_modification",
            () -> new SimpleCraftingRecipeSerializer<>(UrnModificationRecipe::new)
    );
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UrnUpgradeRecipe>> URN_UPGRADE = register("urn_upgrade",
            () -> new ExtendedShapedRecipe.BasicSerializer<>((group, category, pattern, result, showNotification) -> new UrnUpgradeRecipe(group, category, pattern, result))
    );

    private static <T extends RecipeSerializer<?>> DeferredHolder<RecipeSerializer<?>, T> register(String name, Supplier<T> serializer) {
        return REGISTER.register(name, serializer);
    }
}
