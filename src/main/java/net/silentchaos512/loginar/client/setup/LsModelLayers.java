package net.silentchaos512.loginar.client.setup;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.model.LoginarModel;

@EventBusSubscriber
public class LsModelLayers {
    public static final ModelLayerLocation LOGINAR = new ModelLayerLocation(LoginarMod.getId("loginar"), "main");
    public static final ModelLayerLocation LOGINAR_BABY = new ModelLayerLocation(LoginarMod.getId("loginar_baby"), "main");

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LOGINAR, LoginarModel::createLayerDefinition);
        event.registerLayerDefinition(LOGINAR_BABY, LoginarModel::createBabyLayerDefinition);
    }
}
