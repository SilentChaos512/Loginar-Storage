package net.silentchaos512.loginar.event;

import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBackpackScreen;
import net.silentchaos512.loginar.block.urn.LoginarUrnScreen;
import net.silentchaos512.loginar.client.KeyTracker;
import net.silentchaos512.loginar.client.LoginarUrnSwapperScreen;
import net.silentchaos512.loginar.client.model.LoginarModel;
import net.silentchaos512.loginar.client.renderer.LoginarEntityRenderer;
import net.silentchaos512.loginar.item.container.ContainerItemScreen;
import net.silentchaos512.loginar.setup.LsEntityTypes;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.util.Const;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class LsClientModEvents {
    private LsClientModEvents() {}

    @SubscribeEvent
    public static void onFmlClientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(LsItems.LOGINAR_ANTENNA.get(), Const.IS_LOGINAR_CHUNK, (stack, level, entity, par4) -> {
            // TODO: Return 1 for loginar spawn chunks, 0 otherwise (can only calculate on the server...)
            return LoginarMod.RANDOM.nextInt(20) == 0 ? 1 : 0;
        });
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(LsMenuTypes.LOGINAR_URN.get(), LoginarUrnScreen::new);
        event.register(LsMenuTypes.LOGINAR_URN_BACKPACK.get(), LoginarUrnBackpackScreen::new);
        event.register(LsMenuTypes.LOGINAR_URN_SWAPPER.get(), LoginarUrnSwapperScreen::new);

        event.register(LsMenuTypes.LUNCH_BOX.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.GEM_BAG.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.FLOWER_BASKET.get(), ContainerItemScreen::new);
        event.register(LsMenuTypes.ORE_CRATE.get(), ContainerItemScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(LsEntityTypes.LOGINAR.get(), LoginarEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LoginarModel.LAYER_LOCATION, LoginarModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyTracker.SWAP_URN_ITEMS);
    }
}
