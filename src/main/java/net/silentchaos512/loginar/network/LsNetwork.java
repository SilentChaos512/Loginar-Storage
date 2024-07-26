package net.silentchaos512.loginar.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.silentchaos512.loginar.LoginarMod;

@EventBusSubscriber(modid = LoginarMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class LsNetwork {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar("1");
        registrar.playToServer(
                CPacketOpenUrnForItemSwap.TYPE,
                CPacketOpenUrnForItemSwap.STREAM_CODEC,
                (data, ctx) -> LsServerPayloadHandler.getInstance().handleOpenUrnForItemSwap(data, ctx)
        );
        registrar.playToServer(
                CPacketSwapItemFromUrn.TYPE,
                CPacketSwapItemFromUrn.STREAM_CODEC,
                (data, ctx) -> LsServerPayloadHandler.getInstance().handleSwapItemFromUrn(data, ctx)
        );
    }
}
