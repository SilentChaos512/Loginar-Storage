package net.silentchaos512.loginar.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;

public interface TickingUrnUpgrade {
    void tick(LoginarUrnBlockEntity urn, Level level, BlockPos pos);
}
