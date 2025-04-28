package net.silentchaos512.loginar.entity.ai.goal;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class LoginarFloatInWaterGoal extends FloatGoal {
    private final Mob mob;

    public LoginarFloatInWaterGoal(Mob pMob) {
        super(pMob);
        this.mob = pMob;
    }

    @Override
    public boolean canUse() {
        return this.mob.isInWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getFluidJumpThreshold()
                || this.mob.isInFluidType((fluidType, height) -> this.mob.canSwimInFluidType(fluidType) && height > this.mob.getFluidJumpThreshold());
    }
}
