package net.silentchaos512.loginar.event;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.silentchaos512.loginar.entity.LoginarEntity;
import net.silentchaos512.loginar.setup.LsEntityTypes;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class LsCommonModEvents {
    private LsCommonModEvents() {}

    @SubscribeEvent
    public static void onRegisterSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
                LsEntityTypes.LOGINAR.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LoginarEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(LsEntityTypes.LOGINAR.get(), LoginarEntity.createAttributes());
    }
}
