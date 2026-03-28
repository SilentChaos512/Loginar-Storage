package net.silentchaos512.loginar.setup;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapedRecipe;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.crafting.recipe.UrnBaseRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnModificationRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnUpgradeRecipe;

import java.util.function.Supplier;

public class LsRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, LoginarMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UrnBaseRecipe>> URN = register(
            "urn_base",
            () -> UrnBaseRecipe.SERIALIZER
    );
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UrnModificationRecipe>> URN_MODIFICATION = register(
            "urn_modification",
            () -> new RecipeSerializer<>(MapCodec.unit(UrnModificationRecipe.INSTANCE), StreamCodec.unit(UrnModificationRecipe.INSTANCE))
    );
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UrnUpgradeRecipe>> URN_UPGRADE = register(
            "urn_upgrade",
            () -> ExtendedShapedRecipe.basicSerializer(UrnUpgradeRecipe::new)
    );

    private static <T extends RecipeSerializer<?>> DeferredHolder<RecipeSerializer<?>, T> register(String name, Supplier<T> serializer) {
        return REGISTER.register(name, serializer);
    }
}
