package net.silentchaos512.loginar.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.silentchaos512.loginar.api.TickingUrnUpgrade;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;
import net.silentchaos512.loginar.block.urn.UrnHelper;

public class VacuumUrnUpgrade extends UpgradeItem implements TickingUrnUpgrade {
    private static final int RANGE = 4;

    public VacuumUrnUpgrade(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LoginarUrnBlockEntity urn, Level level, BlockPos pos) {
//        if (!state.getLidState().isOpen()) return;

        Vec3 target = new Vec3(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
        AABB axisAlignedBB = new AABB(
                pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE,
                pos.getX() + RANGE + 1, pos.getY() + RANGE, pos.getZ() + RANGE + 1
        );
        boolean itemsAbsorbed = false;

        for (ItemEntity entity : level.getEntitiesOfClass(ItemEntity.class, axisAlignedBB)) {
            double distanceSq = entity.distanceToSqr(target.x, target.y, target.z);
            if (distanceSq < 0.75) {
                // Try to add item to urn's inventory
                if (UrnHelper.tryAddItem(urn, entity.getItem())) {
                    itemsAbsorbed = true;
                    if (entity.getItem().isEmpty()) {
                        entity.remove(Entity.RemovalReason.DISCARDED);
                    }
                }
            } else {
                // Accelerate to target point
                Vec3 vec = entity.getEyePosition(0f).subtract(target);
                vec = vec.normalize().scale(0.05);
                if (entity.position().y < target.y) {
                    double xzDistanceSq = ((entity.position().x - target.x) * (entity.position().x - target.x)) + ((entity.position().z - target.z) * (entity.position().z - target.z));
                    vec = vec.add(0, -(0.01 + xzDistanceSq / 250), 0);
                }
                // Slow down a bit to prevent orbiting, then add new velocity
                final Vec3 slowdown = entity.getDeltaMovement().scale(-0.05);
                entity.push(slowdown.x, slowdown.y, slowdown.z);
                entity.push(-vec.x, -vec.y, -vec.z);
            }
        }

        if (itemsAbsorbed) {
            urn.setChanged();
            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7f, 0.5f);
            spawnParticles(level, pos);
        }
    }

    private static void spawnParticles(Level level, BlockPos pos) {
        // FIXME: Does not work?
        for (int i = 0; i < 5; ++i) {
            level.addParticle(
                    ParticleTypes.SMOKE,
                    pos.getX() + 0.5,
                    pos.getY() + 1.5,
                    pos.getZ() + 0.5,
                    0.05 * level.getRandom().nextGaussian(),
                    0.05 * level.getRandom().nextGaussian(),
                    0.05 * level.getRandom().nextGaussian()
            );
        }
    }
}
