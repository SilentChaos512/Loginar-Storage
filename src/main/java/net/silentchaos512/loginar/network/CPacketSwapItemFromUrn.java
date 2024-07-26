package net.silentchaos512.loginar.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.silentchaos512.loginar.LoginarMod;

public record CPacketSwapItemFromUrn(int urnItemSlot) implements CustomPacketPayload {
    public static final Type<CPacketSwapItemFromUrn> TYPE = new Type<>(LoginarMod.getId("swap_item_from_urn"));

    public static final StreamCodec<FriendlyByteBuf, CPacketSwapItemFromUrn> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, d -> d.urnItemSlot,
            CPacketSwapItemFromUrn::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
