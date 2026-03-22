package net.silentchaos512.loginar.level.placement;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.silentchaos512.loginar.entity.WildLoginar;
import net.silentchaos512.loginar.setup.LsPlacementModifierTypes;

public class LoginarChunkFilter extends PlacementFilter {
    public static final LoginarChunkFilter INSTANCE = new LoginarChunkFilter();

    public static final MapCodec<LoginarChunkFilter> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return WildLoginar.isSpawningChunk(context.getLevel(), pos);
    }

    @Override
    public PlacementModifierType<?> type() {
        return LsPlacementModifierTypes.LOGINAR_CHUNK.get();
    }
}
