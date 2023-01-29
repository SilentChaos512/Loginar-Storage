package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
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
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(LsItems.LOGINAR_TENTACLE), LsItems.LOGINAR_CALAMARI, 0.35f, 200)
                .unlockedBy("has_item", has(LsItems.LOGINAR_TENTACLE))
                .save(consumer, "loginar_calamari_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(LsItems.LOGINAR_TENTACLE), LsItems.LOGINAR_CALAMARI, 0.35f, 100)
                .unlockedBy("has_item", has(LsItems.LOGINAR_TENTACLE))
                .save(consumer, "loginar_calamari_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(LsItems.LOGINAR_TENTACLE), LsItems.LOGINAR_CALAMARI, 0.35f, 600)
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

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), LsBlocks.LARGE_LOGINAR_URN)
                .patternLine("cnw")
                .patternLine("o*o")
                .patternLine("###")
                .key('*', LsBlocks.MEDIUM_LOGINAR_URN)
                .key('o', Items.ENDER_EYE)
                .key('n', Tags.Items.INGOTS_NETHERITE)
                .key('c', Items.CRIMSON_FUNGUS)
                .key('w', Items.WARPED_FUNGUS)
                .key('#', Blocks.CRYING_OBSIDIAN)
                .addCriterion("has_item", has(LsBlocks.MEDIUM_LOGINAR_URN))
                .build(consumer);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), LsBlocks.HUGE_LOGINAR_URN)
                .patternLine("csc")
                .patternLine("e*e")
                .patternLine("###")
                .key('*', LsBlocks.LARGE_LOGINAR_URN)
                .key('c', Items.CHORUS_FLOWER)
                .key('s', Items.SHULKER_SHELL)
                .key('e', Tags.Items.GEMS_EMERALD)
                .key('#', Blocks.PURPUR_BLOCK)
                .addCriterion("has_item", has(LsBlocks.LARGE_LOGINAR_URN))
                .build(consumer);

        shapedBuilder(LsRecipeSerializers.URN_UPGRADE.get(), LsBlocks.SUPER_LOGINAR_URN)
                .patternLine("wsw")
                .patternLine("p*p")
                .patternLine("###")
                .key('*', LsBlocks.HUGE_LOGINAR_URN)
                .key('w', Items.WITHER_ROSE)
                .key('s', Items.SCULK)
                .key('p', Tags.Items.GEMS_PRISMARINE)
                .key('#', Blocks.PRISMARINE)
                .addCriterion("has_item", has(LsBlocks.HUGE_LOGINAR_URN))
                .build(consumer);

        // Upgrade recipes
        shapedBuilder(LsItems.BACKPACK_UPGRADE)
                .patternLine(" e ")
                .patternLine(" l ")
                .patternLine("lal")
                .key('a', LsItems.LOGINAR_ANTENNA)
                .key('e', Tags.Items.ENDER_PEARLS)
                .key('l', Tags.Items.LEATHER)
                .addCriterion("has_item", has(LsItems.LOGINAR_ANTENNA))
                .build(consumer);

        shapedBuilder(LsItems.ITEM_SWAPPER_UPGRADE)
                .patternLine(" w ")
                .patternLine("i i")
                .patternLine("waw")
                .key('a', LsItems.LOGINAR_ANTENNA)
                .key('i', Tags.Items.INGOTS_GOLD)
                .key('w', ItemTags.WOOL)
                .addCriterion("has_item", has(LsItems.LOGINAR_ANTENNA))
                .build(consumer);

        shapedBuilder(LsItems.VACUUM_UPGRADE)
                .patternLine(" i ")
                .patternLine("rhr")
                .patternLine("iai")
                .key('a', LsItems.LOGINAR_ANTENNA)
                .key('h', Items.HOPPER)
                .key('i', Tags.Items.INGOTS_COPPER)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_item", has(LsItems.LOGINAR_ANTENNA))
                .build(consumer);
    }

    private void baseUrn(Consumer<FinishedRecipe> consumer, ItemLike clay, int clayColor) {
        String blockName = NameUtils.fromItem(clay).getPath();
        int i = blockName.lastIndexOf('_');
        String colorName = "";
        if (i > -1) {
            colorName = blockName.substring(0, i + 1);
        }

        shapedBuilder(LsRecipeSerializers.URN_BASE.get(), LsBlocks.TINY_LOGINAR_URN)
                .addExtraData(json -> json.addProperty("clay_color", Color.format(clayColor)))
                .patternLine("#~#")
                .patternLine("#0#")
                .patternLine("###")
                .key('#', clay)
                .key('~', LsItems.LOGINAR_ANTENNA)
                .key('0', Tags.Items.GEMS)
                .addCriterion("has_item", has(LsItems.LOGINAR_ANTENNA))
                .build(consumer, modId(colorName + "tiny_loginar_urn"));
    }
}
