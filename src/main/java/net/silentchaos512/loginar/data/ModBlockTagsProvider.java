package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsTags;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator gen, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, LoginarMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(LsTags.Blocks.URNS).add(LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));

        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(LsTags.Blocks.URNS);
    }
}
