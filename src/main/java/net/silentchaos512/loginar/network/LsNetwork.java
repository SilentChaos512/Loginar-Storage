package net.silentchaos512.loginar.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.silentchaos512.loginar.LoginarMod;

@EventBusSubscriber(modid = LoginarMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class LsNetwork {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar("2");
        registrar.playToServer(
                OpenBackpackUrnPayload.TYPE,
                OpenBackpackUrnPayload.STREAM_CODEC,
                (data, ctx) -> LsServerPayloadHandler.getInstance().handleOpenBackpackUrn(data, ctx)
        );
        registrar.playToServer(
                OpenUrnForItemSwapPayload.TYPE,
                OpenUrnForItemSwapPayload.STREAM_CODEC,
                (data, ctx) -> LsServerPayloadHandler.getInstance().handleOpenUrnForItemSwap(data, ctx)
        );
        registrar.playToServer(
                SwapItemFromUrnPayload.TYPE,
                SwapItemFromUrnPayload.STREAM_CODEC,
                (data, ctx) -> LsServerPayloadHandler.getInstance().handleSwapItemFromUrn(data, ctx)
        );
    }
}
