package net.silentchaos512.loginar.data;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.silentchaos512.lib.data.recipe.ExtendedShapedRecipeBuilder;
import net.silentchaos512.lib.data.recipe.LibRecipeProvider;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.crafting.recipe.UrnBaseRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnModificationRecipe;
import net.silentchaos512.loginar.crafting.recipe.UrnUpgradeRecipe;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsTags;

public class ModRecipeProvider extends LibRecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
        super(registries, recipeOutput, LoginarMod.MOD_ID);
    }

    @Override
    protected void buildRecipes() {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(LsItems.LOGINAR_TENTACLE), RecipeCategory.FOOD, CookingBookCategory.FOOD, LsItems.LOGINAR_CALAMARI, 0.35f, 200)
                .unlockedBy("has_item", has(LsItems.LOGINAR_TENTACLE))
                .save(this.output, modId("loginar_calamari_smelting"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(LsItems.LOGINAR_TENTACLE), RecipeCategory.FOOD, LsItems.LOGINAR_CALAMARI, 0.35f, 100)
                .unlockedBy("has_item", has(LsItems.LOGINAR_TENTACLE))
                .save(this.output, modId("loginar_calamari_smoking"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(LsItems.LOGINAR_TENTACLE), RecipeCategory.FOOD, LsItems.LOGINAR_CALAMARI, 0.35f, 600)
                .unlockedBy("has_item", has(LsItems.LOGINAR_TENTACLE))
                .save(this.output, modId("loginar_calamari_campfire_cooking"));

        // Crafting items

        // Tiny Crystal
        shapeless(RecipeCategory.MISC, LsItems.TINY_CRYSTAL)
                .requires(LsBlocks.FIRE_FLOWER)
                .unlockedBy("has_item", has(LsBlocks.FIRE_FLOWER))
                .save(this.output);
        shapeless(RecipeCategory.MISC, LsItems.TINY_CRYSTAL, 2)
                .requires(LsItems.SMALL_CRYSTAL)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output, modId("tiny_crystal_from_small"));
        // Small Crystal
        shaped(RecipeCategory.MISC, LsItems.SMALL_CRYSTAL)
                .pattern("#a#")
                .pattern("#*#")
                .pattern("#b#")
                .define('*', LsItems.TINY_CRYSTAL)
                .define('#', Tags.Items.INGOTS_COPPER)
                .define('a', Tags.Items.GEMS_AMETHYST)
                .define('b', Items.GLOW_BERRIES)
                .unlockedBy("has_item", has(LsItems.TINY_CRYSTAL))
                .save(this.output);
        shapeless(RecipeCategory.MISC, LsItems.SMALL_CRYSTAL, 2)
                .requires(LsItems.SMALL_CRYSTAL)
                .requires(Tags.Items.INGOTS_COPPER)
                .requires(Tags.Items.INGOTS_COPPER)
                .requires(Tags.Items.INGOTS_COPPER)
                .requires(Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output, modId("small_crystal_clone"));
        shapeless(RecipeCategory.MISC, LsItems.SMALL_CRYSTAL)
                .requires(LsItems.LOGINAR_ANTENNA)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(this.output, modId("easy_small_crystal"));
        // Medium Crystal
        shaped(RecipeCategory.MISC, LsItems.MEDIUM_CRYSTAL)
                .pattern("/g/")
                .pattern("q*q")
                .pattern("###")
                .define('*', LsItems.SMALL_CRYSTAL)
                .define('/', Tags.Items.RODS_BLAZE)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('q', Tags.Items.GEMS_QUARTZ)
                .define('#', Blocks.BLACKSTONE)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output);
        shapeless(RecipeCategory.MISC, LsItems.MEDIUM_CRYSTAL, 2)
                .requires(LsItems.MEDIUM_CRYSTAL)
                .requires(Tags.Items.GEMS_QUARTZ)
                .requires(Tags.Items.GEMS_QUARTZ)
                .requires(Tags.Items.GEMS_QUARTZ)
                .requires(Tags.Items.GEMS_QUARTZ)
                .unlockedBy("has_item", has(LsItems.MEDIUM_CRYSTAL))
                .save(this.output, modId("medium_crystal_clone"));
        // Large Crystal
        shaped(RecipeCategory.MISC, LsItems.LARGE_CRYSTAL)
                .pattern("cnw")
                .pattern("o*o")
                .pattern("###")
                .define('*', LsItems.MEDIUM_CRYSTAL)
                .define('o', Items.ENDER_EYE)
                .define('n', Tags.Items.INGOTS_NETHERITE)
                .define('c', Items.CRIMSON_FUNGUS)
                .define('w', Items.WARPED_FUNGUS)
                .define('#', Blocks.CRYING_OBSIDIAN)
                .unlockedBy("has_item", has(LsItems.MEDIUM_CRYSTAL))
                .save(this.output);
        shapeless(RecipeCategory.MISC, LsItems.LARGE_CRYSTAL, 2)
                .requires(LsItems.LARGE_CRYSTAL)
                .requires(Items.ENDER_EYE, 3)
                .unlockedBy("has_item", has(LsItems.LARGE_CRYSTAL))
                .save(this.output, modId("large_crystal_clone"));
        // Huge Crystal
        shaped(RecipeCategory.MISC, LsItems.HUGE_CRYSTAL)
                .pattern("csc")
                .pattern("e*e")
                .pattern("###")
                .define('*', LsItems.LARGE_CRYSTAL)
                .define('c', Items.CHORUS_FLOWER)
                .define('s', Items.SHULKER_SHELL)
                .define('e', Tags.Items.GEMS_EMERALD)
                .define('#', Blocks.PURPUR_BLOCK)
                .unlockedBy("has_item", has(LsItems.LARGE_CRYSTAL))
                .save(this.output);
        shapeless(RecipeCategory.MISC, LsItems.HUGE_CRYSTAL, 2)
                .requires(LsItems.HUGE_CRYSTAL)
                .requires(Items.CHORUS_FLOWER, 2)
                .unlockedBy("has_item", has(LsItems.HUGE_CRYSTAL))
                .save(this.output, modId("huge_crystal_clone"));
        // Super Crystal
        shaped(RecipeCategory.MISC, LsItems.SUPER_CRYSTAL)
                .pattern("wsw")
                .pattern("p*p")
                .pattern("###")
                .define('*', LsItems.HUGE_CRYSTAL)
                .define('w', Items.WITHER_ROSE)
                .define('s', Items.SCULK)
                .define('p', Tags.Items.GEMS_PRISMARINE)
                .define('#', Blocks.PRISMARINE)
                .unlockedBy("has_item", has(LsItems.HUGE_CRYSTAL))
                .save(this.output);
        shapeless(RecipeCategory.MISC, LsItems.SUPER_CRYSTAL, 2)
                .requires(LsItems.SUPER_CRYSTAL)
                .requires(Items.WITHER_ROSE)
                .unlockedBy("has_item", has(LsItems.SUPER_CRYSTAL))
                .save(this.output, modId("super_crystal_clone"));

        // Loginar Urn recipes

        registerCustomRecipe(this.output, UrnModificationRecipe::new, LoginarMod.getId("urn_modification"));

        baseUrn(Blocks.TERRACOTTA, UrnHelper.DEFAULT_CLAY_COLOR.getColor());
        baseUrn(Blocks.WHITE_TERRACOTTA, 0xD1B1A1);
        baseUrn(Blocks.ORANGE_TERRACOTTA, 0xA05325);
        baseUrn(Blocks.MAGENTA_TERRACOTTA, 0x95576C);
        baseUrn(Blocks.LIGHT_BLUE_TERRACOTTA, 0x706C8A);
        baseUrn(Blocks.YELLOW_TERRACOTTA, 0xB98423);
        baseUrn(Blocks.LIME_TERRACOTTA, 0x677534);
        baseUrn(Blocks.PINK_TERRACOTTA, 0xA04D4E);
        baseUrn(Blocks.GRAY_TERRACOTTA, 0x392A24);
        baseUrn(Blocks.LIGHT_GRAY_TERRACOTTA, 0x876A61);
        baseUrn(Blocks.CYAN_TERRACOTTA, 0x565A5B);
        baseUrn(Blocks.PURPLE_TERRACOTTA, 0x764556);
        baseUrn(Blocks.BLUE_TERRACOTTA, 0x4A3B5B);
        baseUrn(Blocks.BROWN_TERRACOTTA, 0x4D3224);
        baseUrn(Blocks.GREEN_TERRACOTTA, 0x4B522A);
        baseUrn(Blocks.RED_TERRACOTTA, 0x8E3C2E);
        baseUrn(Blocks.BLACK_TERRACOTTA, 0x251610);

        upgradedUrn(LsBlocks.SMALL_LOGINAR_URN)
                .pattern("/")
                .pattern("*")
                .define('*', LsBlocks.TINY_LOGINAR_URN)
                .define('/', LsItems.SMALL_CRYSTAL)
                .unlockedBy("has_item", has(LsBlocks.TINY_LOGINAR_URN))
                .save(this.output);

        upgradedUrn(LsBlocks.MEDIUM_LOGINAR_URN)
                .pattern("/")
                .pattern("*")
                .define('*', LsBlocks.SMALL_LOGINAR_URN)
                .define('/', LsItems.MEDIUM_CRYSTAL)
                .unlockedBy("has_item", has(LsBlocks.SMALL_LOGINAR_URN))
                .save(this.output);

        upgradedUrn(LsBlocks.LARGE_LOGINAR_URN)
                .pattern("/")
                .pattern("*")
                .define('*', LsBlocks.MEDIUM_LOGINAR_URN)
                .define('/', LsItems.LARGE_CRYSTAL)
                .unlockedBy("has_item", has(LsBlocks.MEDIUM_LOGINAR_URN))
                .save(this.output);

        upgradedUrn(LsBlocks.HUGE_LOGINAR_URN)
                .pattern("/")
                .pattern("*")
                .define('*', LsBlocks.LARGE_LOGINAR_URN)
                .define('/', LsItems.HUGE_CRYSTAL)
                .save(this.output);

        upgradedUrn(LsBlocks.SUPER_LOGINAR_URN)
                .pattern("/")
                .pattern("*")
                .define('*', LsBlocks.HUGE_LOGINAR_URN)
                .define('/', LsItems.SUPER_CRYSTAL)
                .unlockedBy("has_item", has(LsBlocks.HUGE_LOGINAR_URN))
                .save(this.output);

        // Upgrade recipes
        shaped(RecipeCategory.MISC, LsItems.BACKPACK_UPGRADE)
                .pattern(" e ")
                .pattern(" l ")
                .pattern("lal")
                .define('a', LsItems.LOGINAR_ANTENNA)
                .define('e', Tags.Items.ENDER_PEARLS)
                .define('l', Tags.Items.LEATHERS)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(this.output);

        shaped(RecipeCategory.MISC, LsItems.ITEM_SWAPPER_UPGRADE)
                .pattern(" w ")
                .pattern("i i")
                .pattern("waw")
                .define('a', LsItems.LOGINAR_ANTENNA)
                .define('i', Tags.Items.INGOTS_GOLD)
                .define('w', ItemTags.WOOL)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(this.output);

        shaped(RecipeCategory.MISC, LsItems.VACUUM_UPGRADE)
                .pattern(" i ")
                .pattern("rhr")
                .pattern("iai")
                .define('a', LsItems.LOGINAR_ANTENNA)
                .define('h', Items.HOPPER)
                .define('i', Tags.Items.INGOTS_COPPER)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(this.output);

        shaped(RecipeCategory.MISC, LsItems.SUPPLIER_UPGRADE)
                .pattern(" / ")
                .pattern("/a/")
                .pattern("###")
                .define('a', LsItems.LOGINAR_ANTENNA)
                .define('#', Blocks.BAMBOO_BLOCK)
                .define('/', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_item", has(LsItems.LOGINAR_ANTENNA))
                .save(this.output);

        // Container items

        shaped(RecipeCategory.FOOD, LsItems.LUNCH_BOX)
                .pattern(" / ")
                .pattern("#a#")
                .pattern("#c#")
                .define('/', Tags.Items.RODS_WOODEN)
                .define('#', Blocks.RED_TERRACOTTA)
                .define('a', LsItems.SMALL_CRYSTAL)
                .define('c', LsItems.LOGINAR_CALAMARI)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output);

        shaped(RecipeCategory.BREWING, LsItems.POTION_POUCH)
                .pattern("#/#")
                .pattern("#a#")
                .pattern(" # ")
                .define('a', LsItems.SMALL_CRYSTAL)
                .define('/', Tags.Items.RODS_BLAZE)
                .define('#', Tags.Items.LEATHERS)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output);

        shaped(RecipeCategory.TOOLS, LsItems.GEM_BAG)
                .pattern("/~/")
                .pattern("#g#")
                .pattern("###")
                .define('~', LsItems.SMALL_CRYSTAL)
                .define('/', Tags.Items.NUGGETS_GOLD)
                .define('#', ItemTags.WOOL)
                .define('g', Tags.Items.GEMS)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output);

        shaped(RecipeCategory.TOOLS, LsItems.FLOWER_BASKET)
                .pattern("/~/")
                .pattern("#g#")
                .pattern("###")
                .define('~', LsItems.SMALL_CRYSTAL)
                .define('/', Tags.Items.NUGGETS_GOLD)
                .define('#', Ingredient.of(Items.SUGAR_CANE, Items.BAMBOO))
                .define('g', ItemTags.FLOWERS)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output);

        shaped(RecipeCategory.TOOLS, LsItems.ORE_CRATE)
                .pattern("ooo")
                .pattern("#~#")
                .pattern("###")
                .define('o', Tags.Items.RAW_MATERIALS)
                .define('~', LsItems.SMALL_CRYSTAL)
                .define('#', ItemTags.PLANKS)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output);

        shaped(RecipeCategory.TOOLS, LsItems.SEED_BAG)
                .pattern(" ~ ")
                .pattern("#s#")
                .pattern(" # ")
                .define('~', LsItems.SMALL_CRYSTAL)
                .define('#', ItemTags.WOOL)
                .define('s', Tags.Items.SEEDS)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output);

        shaped(RecipeCategory.TOOLS, LsItems.WOOD_RACK)
                .pattern("/ /")
                .pattern("/~/")
                .pattern("# #")
                .define('~', LsItems.SMALL_CRYSTAL)
                .define('/', Tags.Items.RODS_WOODEN)
                .define('#', ItemTags.LOGS)
                .unlockedBy("has_item", has(LsItems.SMALL_CRYSTAL))
                .save(this.output);

        // Misc

        shapeless(RecipeCategory.TOOLS, LsItems.FIRE_FLINGER)
                .requires(LsItems.LOGINAR_ANTENNA)
                .requires(LsTags.Items.GEMS_FIRE_PEARL)
                .unlockedBy("has_item", has(LsItems.FIRE_PEARL))
                .save(this.output);
    }

    private void baseUrn(ItemLike clay, int clayColor) {
        String blockName = NameUtils.fromItem(clay).getPath();
        int i = blockName.lastIndexOf('_');
        String colorName = "";
        if (i > -1) {
            colorName = blockName.substring(0, i + 1);
        }

        new UrnRecipeBuilder(this.items, clayColor)
                .group("loginar:tiny_urns")
                .pattern("#~#")
                .pattern("#0#")
                .pattern("###")
                .define('#', clay)
                .define('~', LsItems.TINY_CRYSTAL)
                .define('0', Tags.Items.GEMS)
                .unlockedBy("has_item", has(LsItems.TINY_CRYSTAL))
                .save(this.output, modId(colorName + "tiny_loginar_urn"));
    }

    private UrnUpgradeRecipeBuilder upgradedUrn(ItemLike upgradedUrn) {
        return new UrnUpgradeRecipeBuilder(this.items, upgradedUrn);
    }

    private static class UrnRecipeBuilder extends ExtendedShapedRecipeBuilder<UrnBaseRecipe> {
        private final int clayColor;

        public UrnRecipeBuilder(HolderGetter<Item> items, int clayColor) {
            super(items, RecipeCategory.DECORATIONS, new ItemStackTemplate(LsBlocks.TINY_LOGINAR_URN.asItem()));
            this.clayColor = clayColor;
        }

        @Override
        public UrnBaseRecipe createRecipe(ResourceKey<Recipe<?>> id) {
            ShapedRecipePattern pattern = ShapedRecipePattern.of(this.key, this.rows);
            return new UrnBaseRecipe(
                    this.commonInfo,
                    this.bookInfo,
                    pattern,
                    this.result,
                    new Color(clayColor)
            );
        }
    }

    private static class UrnUpgradeRecipeBuilder extends ExtendedShapedRecipeBuilder<UrnUpgradeRecipe> {
        public UrnUpgradeRecipeBuilder(HolderGetter<Item> items, ItemLike upgradedUrn) {
            super(items, RecipeCategory.DECORATIONS, new ItemStackTemplate(upgradedUrn.asItem()));
        }

        public UrnUpgradeRecipe createRecipe(ResourceKey<Recipe<?>> id) {
            ShapedRecipePattern pattern = ShapedRecipePattern.of(this.key, this.rows);
            return new UrnUpgradeRecipe(
                    this.commonInfo,
                    this.bookInfo,
                    pattern,
                    this.result
            );
        }
    }
}
