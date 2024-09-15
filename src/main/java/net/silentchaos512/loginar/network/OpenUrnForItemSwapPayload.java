package net.silentchaos512.loginar.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.silentchaos512.loginar.LoginarMod;

public record OpenUrnForItemSwapPayload() implements CustomPacketPayload {
    public static final Type<OpenUrnForItemSwapPayload> TYPE = new Type<>(LoginarMod.getId("open_urn_for_swapping"));

    public static final StreamCodec<FriendlyByteBuf, OpenUrnForItemSwapPayload> STREAM_CODEC = StreamCodec.unit(
            new OpenUrnForItemSwapPayload()
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
