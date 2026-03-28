package net.silentchaos512.loginar.client;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.setup.LsBlocks;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public final class ColorHandlers {
    private ColorHandlers() {}

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(urnClay(), urnGem()), LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));
    }

    private static BlockTintSource urnClay() {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return UrnHelper.DEFAULT_CLAY_COLOR.getColor();
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                if (level.getBlockEntity(pos) instanceof LoginarUrnBlockEntity urnBlockEntity) {
                    return urnBlockEntity.getClayColor().getColor();
                }
                return color(state);
            }
        };
    }

    private static BlockTintSource urnGem() {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return UrnHelper.DEFAULT_GEM_COLOR.getColor();
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                if (level.getBlockEntity(pos) instanceof LoginarUrnBlockEntity urnBlockEntity) {
                    return urnBlockEntity.getGemColor().getColor();
                }
                return color(state);
            }
        };
    }
}
