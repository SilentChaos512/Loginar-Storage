package net.silentchaos512.loginar.entity.ai.goal;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.level.pathfinder.PathType;

public class LoginarFollowOwnerGoal extends FollowOwnerGoal {
    private final TamableAnimal tamable;

    public LoginarFollowOwnerGoal(TamableAnimal pTamable, double pSpeedModifier, float pStartDistance, float pStopDistance) {
        super(pTamable, pSpeedModifier, pStartDistance, pStopDistance);
        this.tamable = pTamable;
    }

    @Override
    public void start() {
        super.start();
        // Prevent loginars from going into water
        this.tamable.setPathfindingMalus(PathType.WATER, -Float.MAX_VALUE);
    }
}
