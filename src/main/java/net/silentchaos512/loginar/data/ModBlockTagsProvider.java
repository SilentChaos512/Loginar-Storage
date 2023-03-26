package net.silentchaos512.loginar.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsTags;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), LoginarMod.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(LsTags.Blocks.URNS).add(LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));

        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(LsTags.Blocks.URNS);
    }
}
