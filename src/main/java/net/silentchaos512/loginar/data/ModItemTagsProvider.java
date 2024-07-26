package net.silentchaos512.loginar.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsTags;

import java.util.Arrays;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(GatherDataEvent event, BlockTagsProvider blocks) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), blocks.contentsGetter(), LoginarMod.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        copy(LsTags.Blocks.URNS, LsTags.Items.URNS);
        tag(LsTags.Items.URNS_CANNOT_STORE)
                .add(
                        Items.SHULKER_BOX,
                        Items.WHITE_SHULKER_BOX,
                        Items.ORANGE_SHULKER_BOX,
                        Items.MAGENTA_SHULKER_BOX,
                        Items.LIGHT_BLUE_SHULKER_BOX,
                        Items.YELLOW_SHULKER_BOX,
                        Items.LIME_SHULKER_BOX,
                        Items.PINK_SHULKER_BOX,
                        Items.GRAY_SHULKER_BOX,
                        Items.LIGHT_GRAY_SHULKER_BOX,
                        Items.CYAN_SHULKER_BOX,
                        Items.PURPLE_SHULKER_BOX,
                        Items.BLUE_SHULKER_BOX,
                        Items.BROWN_SHULKER_BOX,
                        Items.GREEN_SHULKER_BOX,
                        Items.RED_SHULKER_BOX,
                        Items.BLACK_SHULKER_BOX
                );
        builder(LsTags.Items.URN_UPGRADES,
                LsItems.BACKPACK_UPGRADE,
                LsItems.VACUUM_UPGRADE,
                LsItems.ITEM_SWAPPER_UPGRADE
        );
        tag(LsTags.Items.FLOWER_BASKET_CAN_STORE).addTag(ItemTags.FLOWERS);
        tag(LsTags.Items.GEM_BAG_CAN_STORE).addTag(Tags.Items.GEMS);
        tag(LsTags.Items.ORE_CRATE_CAN_STORE).addTag(Tags.Items.RAW_MATERIALS).addTag(Tags.Items.ORES);

        builder(ItemTags.WOLF_FOOD, LsItems.LOGINAR_CALAMARI);
    }

    private void builder(TagKey<Item> tag, ItemLike... items) {
        tag(tag).add(Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new));
    }
}
