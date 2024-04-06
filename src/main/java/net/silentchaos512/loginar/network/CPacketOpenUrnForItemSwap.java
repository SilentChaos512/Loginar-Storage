package net.silentchaos512.loginar.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.silentchaos512.loginar.LoginarMod;

public record CPacketOpenUrnForItemSwap() implements CustomPacketPayload {
    public static final ResourceLocation ID = LoginarMod.getId("open_urn_for_swapping");

    public CPacketOpenUrnForItemSwap(FriendlyByteBuf buf) {
        this();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
