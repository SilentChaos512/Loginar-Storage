package net.silentchaos512.loginar.network;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.silentchaos512.loginar.block.urn.LoginarUrnSwapperMenu;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.util.TextUtil;

import java.util.function.Supplier;

public class OpenUrnSwapperPacket {
    public OpenUrnSwapperPacket() {
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            // Make sure player is not holding an urn...
            if (UrnHelper.isUrn(player.getMainHandItem())) {
                player.sendSystemMessage(TextUtil.misc("swapper.holdingUrn"));
                return;
            }

            // Find and try to open a compatible urn
            ItemStack urn = selectUrnToOpen(player);
            if (!urn.isEmpty()) {
                NetworkHooks.openScreen(
                        player,
                        new SimpleMenuProvider(
                                (id, inv, p) -> new LoginarUrnSwapperMenu(id, inv, urn),
                                Component.empty()
                        ),
                        buf -> buf.writeItem(urn)
                );
            } else {
                player.sendSystemMessage(TextUtil.misc("swapper.noCompatibleUrn"));
            }
        }
    }

    private static ItemStack selectUrnToOpen(ServerPlayer player) {
        // Offhand first
        if (UrnHelper.isUrn(player.getOffhandItem())) {
            return player.getOffhandItem();
        }

        // TODO: Curios support?

        // Other items last
        NonNullList<ItemStack> items = player.getInventory().items;
        for (ItemStack stack : items) {
            if (UrnHelper.isUrn(stack)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    public static OpenUrnSwapperPacket decode(FriendlyByteBuf buf) {
        return new OpenUrnSwapperPacket();
    }

    public static void encode(OpenUrnSwapperPacket msg, FriendlyByteBuf buf) {
    }
}
