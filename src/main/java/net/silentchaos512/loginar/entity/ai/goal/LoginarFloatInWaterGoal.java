package net.silentchaos512.loginar.entity.ai.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.neoforged.neoforge.common.NeoForgeMod;

public class LoginarFloatInWaterGoal extends FloatGoal {
    private final Mob mob;

    public LoginarFloatInWaterGoal(Mob pMob) {
        super(pMob);
        this.mob = pMob;
    }

    @Override
    public boolean canUse() {
        return this.mob.isInWater() && this.mob.getFluidTypeHeight(NeoForgeMod.WATER_TYPE.value()) > this.mob.getFluidJumpThreshold()
                || this.mob.isInFluidType((fluidType, height) -> this.mob.canSwimInFluidType(fluidType) && height > this.mob.getFluidJumpThreshold());
    }
}
