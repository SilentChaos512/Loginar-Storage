package net.silentchaos512.loginar.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.silentchaos512.loginar.LoginarMod;

public record CPacketOpenUrnForItemSwap() implements CustomPacketPayload {
    public static final Type<CPacketOpenUrnForItemSwap> TYPE = new Type<>(LoginarMod.getId("open_urn_for_swapping"));

    public static final StreamCodec<FriendlyByteBuf, CPacketOpenUrnForItemSwap> STREAM_CODEC = StreamCodec.unit(
            new CPacketOpenUrnForItemSwap()
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
