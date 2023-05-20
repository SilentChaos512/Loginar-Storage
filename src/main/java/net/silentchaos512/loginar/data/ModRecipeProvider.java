package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.silentchaos512.lib.data.recipe.LibRecipeProvider;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.UrnData;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;
import net.silentchaos512.utils.Color;

import java.util.function.Consumer;

public class ModRecipeProvider extends LibRecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn, LoginarMod.MOD_ID);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(LsItems.LOGINAR_TENTACLE), RecipeCategory.FOOD, LsItems.LOGINAR_CALAMARI, 0.35f, 200)
                .unlockedBy("has_item", has(LsItems.LOGINAR_TENTACLE))
                .save(consumer, "loginar_calamari_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(LsItems.LOGINAR_TENTACLE), RecipeCategory.FOOD, LsItems.LOGINAR_CALAMARI, 0.35f, 100)
                .unlockedBy("has_item", has(LsItems.LOGINAR_TENTACLE))
                .save(consumer, "loginar_calamari_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(LsItems.LOGINAR_TENTACLE), RecipeCategory.FOOD, LsItems.LOGINAR_CALAMARI, 0.35f, 600)
                .unlockedBy("has_item", has(LsItems.LOGINAR_TENTACLE))
                .save(consumer, "loginar_calamari_campfire_cooking");

        // Loginar Urn recipes

        registerCustomRecipe(consumer, LsRecipeSerializers.URN_MODIFICATION.get());

        baseUrn(consumer, Blocks.TERRACOTTA, UrnData.DEFAULT_CLAY_COLOR);
        baseUrn(consumer, Blocks.WHITE_TERRACOTTA, 0xD1B1A1);
        baseUrn(consumer, Blocks.ORANGE_TERRACOTTA, 0xA05325);
        baseUrn(consumer, Blocks.MAGENTA_TERRACOTTA, 0x95576C);
        baseUrn(consumer, Blocks.LIGHT_BLUE_TERRACOTTA, 0x706C8A);
        baseUrn(consumer, Blocks.YELLOW_TERRACOTTA, 0xB98423);
        baseUrn(consumer, Blocks.LIME_TERRACOTTA, 0x677534);
        baseUrn(consumer, Blocks.PINK_TERRACOTTA, 0xA04D4E);
        baseUrn(consumer, Blocks.GRAY_TERRACOTTA, 0x392A24);
        baseUrn(consumer, Blocks.LIGHT_GRAY_TERRACOTTA, 0x876A61);
        baseUrn(consumer, Blocks.CYAN_TERRACOTTA, 0x565A5B);
        baseUrn(consumer, Blocks.PURPLE_TERRACOTTA, 0x764556);
        baseUrn(consumer, Blocks.BLUE_TERRACOTTA, 0x4A3B5B);
        baseUrn(consumer, Blocks.BROWN_TERRACOTTA, 0x4D3224);
        baseUrn(consumer, Blocks.GREEN_TERRACOTTA, 0x4B522A);
        baseUrn(consumer, Blocks.RED_TERRACOTTA, 0x8E3C2E);
        baseUrn(consumer, Blocks.BLACK_TERRACOTTA, 0x251610);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), RecipeCategory.BUILDING_BLOCKS, LsBlocks.SMALL_LOGINAR_URN)
                .pattern("#a#")
                .pattern("#*#")
                .pattern("#b#")
                .define('*', LsBlocks.TINY_LOGINAR_URN)
                .define('#', Tags.Items.INGOTS_COPPER)
                .define('a', Tags.Items.GEMS_AMETHYST)
                .define('b', Items.GLOW_BERRIES)
                .unlockedBy("has_item", has(LsBlocks.TINY_LOGINAR_URN))
                .save(consumer);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), RecipeCategory.BUILDING_BLOCKS, LsBlocks.MEDIUM_LOGINAR_URN)
                .pattern("/g/")
                .pattern("q*q")
                .pattern("###")
                .define('*', LsBlocks.SMALL_LOGINAR_URN)
                .define('/', Tags.Items.RODS_BLAZE)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('q', Tags.Items.GEMS_QUARTZ)
                .define('#', Blocks.BLACKSTONE)
                .unlockedBy("has_item", has(LsBlocks.SMALL_LOGINAR_URN))
                .save(consumer);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), RecipeCategory.BUILDING_BLOCKS, LsBlocks.LARGE_LOGINAR_URN)
                .pattern("cnw")
                .pattern("o*o")
                .pattern("###")
                .define('*', LsBlocks.MEDIUM_LOGINAR_URN)
                .define('o', Items.ENDER_EYE)
                .define('n', Tags.Items.INGOTS_NETHERITE)
                .define('c', Items.CRIMSON_FUNGUS)
                .define('w', Items.WARPED_FUNGUS)
                .define('#', Blocks.CRYING_OBSIDIAN)
                .unlockedBy("has_item", has(LsBlocks.MEDIUM_LOGINAR_URN))
                .save(consumer);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), RecipeCategory.BUILDING_BLOCKS, LsBlocks.HUGE_LOGINAR_URN)
                .pattern("csc")
                .pattern("e*e")
                .pattern("###")
                .define('*', LsBlocks.LARGE_LOGINAR_URN)
                .define('c', Items.CHORUS_FLOWER)
                .define('s', Items.SHULKER_SHELL)
                .define('e', Tags.Items.GEMS_EMERALD)
                .define('#', Blocks.PURPUR_BLOCK)
                .unlockedBy("has_item", has(LsBlocks.LARGE_LOGINAR_URN))
                .save(consumer);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), RecipeCategory.BUILDING_BLOCKS, LsBlocks.SUPER_LOGINAR_URN)
                .pattern("wsw")
                .pattern("p*p")
                .pattern("###")
                .define('*', LsBlocks.HUGE_LOGINAR_URN)
                .define('w', Items.WITHER_ROSE)
                .define('s', Items.SCULK)
                .define('p', Tags.Items.GEMS_PRISMARINE)
                .define('#', Blocks.PRISMARINE)
                .unlockedBy("has_item", has(LsBlocks.HUGE_LOGINAR_URN))
                .save(consumer);

        // Upgrade recipes
        shapedBuilder(RecipeCategory.MISC, LsItems.BACKPACK_UPGRADE)
                .pattern(" e ")
                .pattern(" l ")
                .pattern("lal")
                .define('a', LsItems.LOGINAR_ANTENNA)
                .define('e', Tags.Items.ENDER_PEARLS)
                .define('l', Tags.Items.LEATHER)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(consumer);

        shapedBuilder(RecipeCategory.MISC, LsItems.ITEM_SWAPPER_UPGRADE)
                .pattern(" w ")
                .pattern("i i")
                .pattern("waw")
                .define('a', LsItems.LOGINAR_ANTENNA)
                .define('i', Tags.Items.INGOTS_GOLD)
                .define('w', ItemTags.WOOL)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(consumer);

        shapedBuilder(RecipeCategory.MISC, LsItems.VACUUM_UPGRADE)
                .pattern(" i ")
                .pattern("rhr")
                .pattern("iai")
                .define('a', LsItems.LOGINAR_ANTENNA)
                .define('h', Items.HOPPER)
                .define('i', Tags.Items.INGOTS_COPPER)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(consumer);

        // Container items

        shapedBuilder(RecipeCategory.FOOD, LsItems.LUNCH_BOX)
                .pattern(" / ")
                .pattern("#a#")
                .pattern("#c#")
                .define('/', Tags.Items.RODS_WOODEN)
                .define('#', Blocks.RED_TERRACOTTA)
                .define('a', LsItems.LOGINAR_ANTENNA)
                .define('c', LsItems.LOGINAR_CALAMARI)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(consumer);

        shapedBuilder(RecipeCategory.MISC, LsItems.GEM_BAG)
                .pattern("/~/")
                .pattern("#g#")
                .pattern("###")
                .define('~', LsItems.LOGINAR_ANTENNA)
                .define('/', Tags.Items.NUGGETS_GOLD)
                .define('#', ItemTags.WOOL)
                .define('g', Tags.Items.GEMS)
                .save(consumer);

        shapedBuilder(RecipeCategory.MISC, LsItems.FLOWER_BASKET)
                .pattern("/~/")
                .pattern("#g#")
                .pattern("###")
                .define('~', LsItems.LOGINAR_ANTENNA)
                .define('/', Tags.Items.NUGGETS_GOLD)
                .define('#', Ingredient.of(Items.SUGAR_CANE, Items.BAMBOO))
                .define('g', ItemTags.FLOWERS)
                .save(consumer);

        shapedBuilder(RecipeCategory.MISC, LsItems.ORE_CRATE)
                .pattern("ooo")
                .pattern("#~#")
                .pattern("###")
                .define('o', Tags.Items.RAW_MATERIALS)
                .define('~', LsItems.LOGINAR_ANTENNA)
                .define('#', ItemTags.PLANKS)
                .save(consumer);
    }

    private void baseUrn(Consumer<FinishedRecipe> consumer, ItemLike clay, int clayColor) {
        String blockName = NameUtils.fromItem(clay).getPath();
        int i = blockName.lastIndexOf('_');
        String colorName = "";
        if (i > -1) {
            colorName = blockName.substring(0, i + 1);
        }

        shapedBuilder(LsRecipeSerializers.URN_BASE.get(), RecipeCategory.BUILDING_BLOCKS, LsBlocks.TINY_LOGINAR_URN)
                .addExtraData(json -> json.addProperty("clay_color", Color.format(clayColor)))
                .pattern("#~#")
                .pattern("#0#")
                .pattern("###")
                .define('#', clay)
                .define('~', LsItems.LOGINAR_ANTENNA)
                .define('0', Tags.Items.GEMS)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(consumer, modId(colorName + "tiny_loginar_urn"));
    }
}
