package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.silentchaos512.lib.data.recipe.LibRecipeProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends LibRecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn, LoginarMod.MOD_ID);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCookingRecipe(consumer, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, LsItems.LOGINAR_TENTACLE, LsItems.LOGINAR_CALAMARI, 0.35f);
        simpleCookingRecipe(consumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, LsItems.LOGINAR_TENTACLE, LsItems.LOGINAR_CALAMARI, 0.35f);
    }
}
