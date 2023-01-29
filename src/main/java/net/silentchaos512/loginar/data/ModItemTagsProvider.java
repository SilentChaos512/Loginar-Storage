package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsTags;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator gen, BlockTagsProvider blocks, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, blocks, LoginarMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(LsTags.Blocks.URNS, LsTags.Items.URNS);
        builder(LsTags.Items.URN_UPGRADES,
                LsItems.BACKPACK_UPGRADE,
                LsItems.VACUUM_UPGRADE,
                LsItems.ITEM_SWAPPER_UPGRADE
        );
    }

    private void builder(TagKey<Item> tag, ItemLike... items) {
        tag(tag).add(Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new));
    }
}
