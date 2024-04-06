package net.silentchaos512.loginar.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.silentchaos512.loginar.LoginarMod;

public record CPacketSwapItemFromUrn(int urnItemSlot) implements CustomPacketPayload {
    public static final ResourceLocation ID = LoginarMod.getId("swap_item_from_urn");

    public CPacketSwapItemFromUrn(FriendlyByteBuf buf) {
        this(buf.readVarInt());
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeVarInt(urnItemSlot());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
