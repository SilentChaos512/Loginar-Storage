package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsTags;
import org.jetbrains.annotations.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator gen, BlockTagsProvider blocks, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, blocks, LoginarMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(LsTags.Blocks.URNS, LsTags.Items.URNS);
    }
}
