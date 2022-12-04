package net.silentchaos512.loginar.entity.ai.goal;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.silentchaos512.loginar.entity.LoginarEntity;
import net.silentchaos512.loginar.setup.LsSounds;

import java.util.EnumSet;

public class LoginarFireballAttackGoal extends Goal {
    private final LoginarEntity loginar;
    private int attackStep;
    private int attackTime;
    private int lastSeen;

    public LoginarFireballAttackGoal(LoginarEntity entity) {
        this.loginar = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.loginar.getTarget();
        return livingentity != null && livingentity.isAlive() && this.loginar.canAttack(livingentity);
    }

    @Override
    public void start() {
        this.attackStep = 0;
    }

    @Override
    public void stop() {
        this.lastSeen = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        --this.attackTime;
        LivingEntity targetEntity = this.loginar.getTarget();
        if (targetEntity != null) {
            boolean hasLineOfSight = this.loginar.getSensing().hasLineOfSight(targetEntity);
            if (hasLineOfSight) {
                this.lastSeen = 0;
            } else {
                ++this.lastSeen;
            }

            double distanceSqr = this.loginar.distanceToSqr(targetEntity);
            if (distanceSqr < 4.0D) {
                if (!hasLineOfSight) {
                    return;
                }

                if (this.attackTime <= 0) {
                    this.attackTime = 20;
                    this.loginar.doHurtTarget(targetEntity);
                }

                this.loginar.getMoveControl().setWantedPosition(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), 1.0D);
            } else if (distanceSqr < this.getFollowDistance() * this.getFollowDistance() && hasLineOfSight) {
                double d1 = targetEntity.getX() - this.loginar.getX();
                double d2 = targetEntity.getY(0.5D) - this.loginar.getY(0.5D);
                double d3 = targetEntity.getZ() - this.loginar.getZ();
                if (this.attackTime <= 0) {
                    ++this.attackStep;
                    if (this.attackStep == 1) {
                        this.attackTime = 60;
                    } else if (this.attackStep <= 3) {
                        this.attackTime = 6;
                    } else {
                        this.attackTime = 100;
                        this.attackStep = 0;
                    }

                    if (this.attackStep > 1) {
                        double d4 = Math.sqrt(Math.sqrt(distanceSqr)) * 0.5D;
                        if (!this.loginar.isSilent()) {
                            this.loginar.level.levelEvent((Player)null, 1018, this.loginar.blockPosition(), 0);
                        }

                        for(int i = 0; i < 1; ++i) {
                            SmallFireball smallfireball = new SmallFireball(this.loginar.level, this.loginar, this.loginar.getRandom().triangle(d1, 2.297D * d4), d2, this.loginar.getRandom().triangle(d3, 2.297D * d4));
                            smallfireball.setPos(smallfireball.getX(), this.loginar.getY(0.5D) + 0.5D, smallfireball.getZ());
                            this.loginar.level.addFreshEntity(smallfireball);
                        }
                    }
                } else if (this.attackTime == 20 && this.attackStep == 1) {
                    loginar.level.playSound(null, loginar, LsSounds.LOGINAR_ATTACK.get(), SoundSource.HOSTILE, 1f, 1f);
                }

                this.loginar.getLookControl().setLookAt(targetEntity, 10.0F, 10.0F);
            } else if (this.lastSeen < 5) {
                this.loginar.getMoveControl().setWantedPosition(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), 1.0D);
            }

            super.tick();
        }
    }

    private double getFollowDistance() {
        return this.loginar.getAttributeValue(Attributes.FOLLOW_RANGE);
    }
}
