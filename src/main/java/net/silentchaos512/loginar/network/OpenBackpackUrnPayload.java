package net.silentchaos512.loginar.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.silentchaos512.loginar.LoginarMod;

public record OpenBackpackUrnPayload() implements CustomPacketPayload {
    public static final Type<OpenBackpackUrnPayload> TYPE = new Type<>(LoginarMod.getId("open_backpack_urn"));

    public static final StreamCodec<FriendlyByteBuf, OpenBackpackUrnPayload> STREAM_CODEC = StreamCodec.unit(
            new OpenBackpackUrnPayload()
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
