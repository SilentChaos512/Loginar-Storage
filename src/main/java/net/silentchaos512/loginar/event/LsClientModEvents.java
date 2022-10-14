package net.silentchaos512.loginar.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silentchaos512.loginar.client.model.LoginarModel;
import net.silentchaos512.loginar.client.renderer.LoginarEntityRenderer;
import net.silentchaos512.loginar.setup.LsEntityTypes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class LsClientModEvents {
    private LsClientModEvents() {}

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(LsEntityTypes.LOGINAR.get(), LoginarEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LoginarModel.LAYER_LOCATION, LoginarModel::createBodyLayer);
    }
}
