package net.silentchaos512.loginar.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, LoginarMod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(LsTags.Blocks.URNS).add(LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));

        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(LsTags.Blocks.URNS);
    }
}
