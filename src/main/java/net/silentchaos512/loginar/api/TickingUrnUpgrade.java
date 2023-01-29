package net.silentchaos512.loginar.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.silentchaos512.loginar.block.urn.UrnData;

public interface TickingUrnUpgrade {
    void tick(UrnData urnData, Level level, BlockPos pos);
}
