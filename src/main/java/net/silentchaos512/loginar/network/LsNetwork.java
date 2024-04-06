package net.silentchaos512.loginar.network;

import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class LsNetwork {
    public static void register(IPayloadRegistrar registrar) {
        registrar.play(CPacketOpenUrnForItemSwap.ID, CPacketOpenUrnForItemSwap::new,
                handler -> handler.server(LsServerPayloadHandler.getInstance()::handleOpenUrnForItemSwap));
        registrar.play(CPacketSwapItemFromUrn.ID, CPacketSwapItemFromUrn::new,
                handler -> handler.server(LsServerPayloadHandler.getInstance()::handleSwapItemFromUrn));
    }
}
