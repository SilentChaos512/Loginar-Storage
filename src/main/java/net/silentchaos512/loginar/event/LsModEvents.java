package net.silentchaos512.loginar.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silentchaos512.loginar.entity.LoginarEntity;
import net.silentchaos512.loginar.setup.LsEntityTypes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class LsModEvents {
    private LsModEvents() {}

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(LsEntityTypes.LOGINAR.get(), LoginarEntity.createAttributes());
    }
}
