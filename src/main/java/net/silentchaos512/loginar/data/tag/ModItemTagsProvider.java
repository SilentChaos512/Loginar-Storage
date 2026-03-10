package net.silentchaos512.loginar.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.silentchaos512.lib.data.tag.LibItemTagsProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsTags;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends LibItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, LoginarMod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        (new ModBlockItemTagsProvider() {
            @Override
            protected TagAppender<Block, Block> tag(TagKey<Block> blockTag, TagKey<Item> itemTag) {
                return new LibItemTagsProvider.BlockToItemConverter(ModItemTagsProvider.this.tag(itemTag));
            }
        }).run();

        builder(LsTags.Items.URNS_CANNOT_STORE); // Empty
        builder(LsTags.Items.URN_UPGRADES,
                LsItems.BACKPACK_UPGRADE,
                LsItems.VACUUM_UPGRADE,
                LsItems.ITEM_SWAPPER_UPGRADE,
                LsItems.SUPPLIER_UPGRADE
        );
        tag(LsTags.Items.FLOWER_BASKET_CAN_STORE)
                .addTag(ItemTags.FLOWERS)
                .add(
                        Items.SMALL_DRIPLEAF,
                        Items.BIG_DRIPLEAF,
                        Items.LILY_PAD,
                        Items.SEAGRASS,
                        Items.SEA_PICKLE,
                        Items.KELP
                );
        tag(LsTags.Items.GEM_BAG_CAN_STORE)
                .addTag(Tags.Items.GEMS)
                .add(Items.HEART_OF_THE_SEA);
        // TODO: Make tags for loginar body armor and boots
        tag(LsTags.Items.LOGINAR_ARMOR_BODY);
        tag(LsTags.Items.LOGINAR_ARMOR_FEET);
        tag(LsTags.Items.LOGINAR_ARMOR)
                .addTag(LsTags.Items.LOGINAR_ARMOR_BODY)
                .addTag(LsTags.Items.LOGINAR_ARMOR_FEET);
        tag(LsTags.Items.LOGINAR_FOOD)
                .addTag(ItemTags.COALS)
                .add(Items.FIRE_CHARGE);
        tag(LsTags.Items.ORE_CRATE_CAN_STORE)
                .addTag(Tags.Items.RAW_MATERIALS)
                .addTag(Tags.Items.ORES)
                .addTag(ItemTags.COALS);
        tag(LsTags.Items.SEED_BAG_CAN_STORE)
                .addTag(Tags.Items.SEEDS)
                .add(Items.CARROT, Items.POTATO, Items.NETHER_WART);
        tag(LsTags.Items.WOOD_RACK_CAN_STORE)
                .addTag(ItemTags.LOGS)
                .addTag(Tags.Items.RODS_WOODEN)
                .add(Items.BAMBOO);

        builder(LsTags.Items.GEMS_FIRE_PEARL, LsItems.FIRE_PEARL);
        tag(Tags.Items.GEMS).addTag(LsTags.Items.GEMS_FIRE_PEARL);

        builder(ItemTags.WOLF_FOOD, LsItems.LOGINAR_CALAMARI);
    }

    private void builder(TagKey<Item> tag, ItemLike... items) {
        tag(tag).add(Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new));
    }
}
