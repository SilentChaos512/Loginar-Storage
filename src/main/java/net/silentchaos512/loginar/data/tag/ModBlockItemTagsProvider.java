package net.silentchaos512.loginar.data.tag;

import net.minecraft.world.level.block.Block;
import net.silentchaos512.lib.data.tag.LibBlockItemTagsProvider;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsTags;

public abstract class ModBlockItemTagsProvider extends LibBlockItemTagsProvider {
    @Override
    public void run() {
        tag(LsTags.Blocks.URNS, LsTags.Items.URNS)
                .addAll(LsBlocks.getUrns().stream().map(b -> (Block) b).toList());
    }
}
