package net.silentchaos512.loginar.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.silentchaos512.loginar.block.urn.UrnData;
import net.silentchaos512.loginar.block.urn.UrnHelper;

import java.util.function.Supplier;

public class HandleUrnItemSwapPacket {
    private final int urnItemSlot;

    public HandleUrnItemSwapPacket(int urnItemSlot) {
        this.urnItemSlot = urnItemSlot;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            ItemStack urn = OpenUrnSwapperPacket.selectUrnToOpen(player);
            if (!urn.isEmpty()) {
                UrnData urnData = UrnData.fromItem(urn);

                ItemStack currentHeldItem = player.getMainHandItem();
                ItemStack swapItem = urnData.items().get(this.urnItemSlot);
                player.setItemInHand(InteractionHand.MAIN_HAND, swapItem);
                urnData.items().set(this.urnItemSlot, currentHeldItem);

                UrnHelper.saveAllItems(urn.getOrCreateTag().getCompound(UrnData.NBT_ROOT), UrnData.NBT_ITEMS, urnData.items(), false);
            }
        }
    }

    public static HandleUrnItemSwapPacket decode(FriendlyByteBuf buf) {
        return new HandleUrnItemSwapPacket(
                buf.readVarInt()
        );
    }

    public static void encode(HandleUrnItemSwapPacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.urnItemSlot);
    }
}
