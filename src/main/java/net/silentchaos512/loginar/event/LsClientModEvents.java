package net.silentchaos512.loginar.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.silentchaos512.loginar.block.urn.LoginarUrnScreen;
import net.silentchaos512.loginar.client.model.LoginarModel;
import net.silentchaos512.loginar.client.renderer.LoginarEntityRenderer;
import net.silentchaos512.loginar.setup.LsEntityTypes;
import net.silentchaos512.loginar.setup.LsMenuTypes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class LsClientModEvents {
    private LsClientModEvents() {}

    @SubscribeEvent
    public static void onFmlClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(LsMenuTypes.LOGINAR_URN.get(), LoginarUrnScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(LsEntityTypes.LOGINAR.get(), LoginarEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LoginarModel.LAYER_LOCATION, LoginarModel::createBodyLayer);
    }
}
