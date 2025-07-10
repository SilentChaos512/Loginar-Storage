package net.silentchaos512.loginar.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.silentchaos512.loginar.block.urn.LoginarUrnBackpackScreen;
import net.silentchaos512.loginar.block.urn.LoginarUrnScreen;
import net.silentchaos512.loginar.block.urn.LoginarUrnSwapperScreen;
import net.silentchaos512.loginar.client.KeyTracker;
import net.silentchaos512.loginar.client.renderer.LoginarRenderer;
import net.silentchaos512.loginar.item.container.ContainerItemScreen;
import net.silentchaos512.loginar.setup.LsEntityTypes;
import net.silentchaos512.loginar.setup.LsMenuTypes;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class LsClientModEvents {
    private LsClientModEvents() {}

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(LsMenuTypes.LOGINAR_URN.get(), LoginarUrnScreen::new);
        event.register(LsMenuTypes.LOGINAR_URN_BACKPACK.get(), LoginarUrnBackpackScreen::new);
        event.register(LsMenuTypes.LOGINAR_URN_SWAPPER.get(), LoginarUrnSwapperScreen::new);

        event.register(LsMenuTypes.LUNCH_BOX.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.POTION_POUCH.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.GEM_BAG.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.FLOWER_BASKET.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.ORE_CRATE.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.SEED_BAG.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.WOOD_RACK.get(), ContainerItemScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(LsEntityTypes.LOGINAR.get(), LoginarRenderer::new);
        event.registerEntityRenderer(LsEntityTypes.FRIENDLY_LOGINAR.get(), LoginarRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyTracker.OPEN_BACKPACK);
        event.register(KeyTracker.SWAP_URN_ITEMS);
    }
}
