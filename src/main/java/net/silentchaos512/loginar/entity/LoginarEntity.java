package net.silentchaos512.loginar.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.silentchaos512.loginar.entity.ai.goal.LoginarFireballAttackGoal;
import net.silentchaos512.loginar.setup.LsSounds;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LoginarEntity extends Monster {
    public LoginarEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 8.0f, 0.7, 1.275));
        this.goalSelector.addGoal(4, new LoginarFireballAttackGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 12.0)
                .build();
    }

    @Override
    public boolean canStandOnFluid(FluidState fluid) {
        return fluid.is(FluidTags.LAVA);
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return LsSounds.LOGINAR_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return LsSounds.LOGINAR_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return LsSounds.LOGINAR_HURT.get();
    }

    public static boolean canSpawn(EntityType<LoginarEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && spawnType == MobSpawnType.SPAWNER
                || (pos.getY() < 9 && isSpawningChunk(level, pos));
    }

    public static boolean isSpawningChunk(ServerLevelAccessor level, BlockPos pos) {
        // Same principle as slime chunks. Picking only certain chunks based on the world seed.
        int x = pos.getX() / 16;
        int z = pos.getZ() / 16;
        Random random = new Random(
                ((WorldGenLevel) level).getSeed()
                + (x * x * 0x9b1aa7)
                + (x * 0x16bc90)
                + (z * z * 0x4ef87a)
                + (z * 0x339abd) ^ 0xac038ff5
        );
        return random.nextInt(5) == 0;
    }
}
