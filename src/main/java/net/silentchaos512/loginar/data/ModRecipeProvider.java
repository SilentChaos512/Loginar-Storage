package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.silentchaos512.lib.data.recipe.LibRecipeProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;

import java.util.function.Consumer;

public class ModRecipeProvider extends LibRecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn, LoginarMod.MOD_ID);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCookingRecipe(consumer, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, LsItems.LOGINAR_TENTACLE, LsItems.LOGINAR_CALAMARI, 0.35f);
        simpleCookingRecipe(consumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, LsItems.LOGINAR_TENTACLE, LsItems.LOGINAR_CALAMARI, 0.35f);

        // Loginar Urn recipes

        // TODO: urn clay colors
        shapedBuilder(LsBlocks.TINY_LOGINAR_URN)
                .patternLine("#~#")
                .patternLine("#0#")
                .patternLine("###")
                .key('#', Blocks.TERRACOTTA)
                .key('~', LsItems.LOGINAR_ANTENNA)
                .key('0', Tags.Items.GEMS)
                .addCriterion("has_item", has(LsItems.LOGINAR_ANTENNA))
                .build(consumer);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), LsBlocks.SMALL_LOGINAR_URN)
                .patternLine("#a#")
                .patternLine("#*#")
                .patternLine("#b#")
                .key('*', LsBlocks.TINY_LOGINAR_URN)
                .key('#', Tags.Items.INGOTS_COPPER)
                .key('a', Tags.Items.GEMS_AMETHYST)
                .key('b', Items.GLOW_BERRIES)
                .addCriterion("has_item", has(LsBlocks.TINY_LOGINAR_URN))
                .build(consumer);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), LsBlocks.MEDIUM_LOGINAR_URN)
                .patternLine("/g/")
                .patternLine("q*q")
                .patternLine("###")
                .key('*', LsBlocks.SMALL_LOGINAR_URN)
                .key('/', Tags.Items.RODS_BLAZE)
                .key('g', Tags.Items.INGOTS_GOLD)
                .key('q', Tags.Items.GEMS_QUARTZ)
                .key('#', Blocks.BLACKSTONE)
                .addCriterion("has_item", has(LsBlocks.SMALL_LOGINAR_URN))
                .build(consumer);
    }
}
