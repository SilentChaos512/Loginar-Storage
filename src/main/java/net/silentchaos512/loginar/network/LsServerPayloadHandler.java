package net.silentchaos512.loginar.network;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.silentchaos512.loginar.block.urn.LoginarUrnSwapperMenu;
import net.silentchaos512.loginar.block.urn.UrnData;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.util.TextUtil;

public class LsServerPayloadHandler {
    private static final LsServerPayloadHandler INSTANCE = new LsServerPayloadHandler();

    public static LsServerPayloadHandler getInstance() {
        return INSTANCE;
    }

    private static void handleData(final PlayPayloadContext ctx, Runnable handler) {
        ctx.workHandler().submitAsync(handler)
                .exceptionally(e -> {
                    ctx.packetHandler().disconnect(Component.translatable("network.loginar.failure", e.getMessage()));
                    return null;
                });
    }

    public void handleOpenUrnForItemSwap(CPacketOpenUrnForItemSwap data, PlayPayloadContext ctx) {
        // Player pressed the item swap key. Search for an urn with a supported item swapper upgrade and open a menu.
        handleData(ctx, () -> {
            ctx.player().ifPresent(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    ItemStack mainHandItem = serverPlayer.getMainHandItem();
                    if (!UrnHelper.isSwappableItem(mainHandItem)) {
                        // TODO: Update message to support other blacklisted items
                        serverPlayer.sendSystemMessage(TextUtil.misc("swapper.holdingUrn", mainHandItem.getDisplayName()));
                        return;
                    }

                    // Find and try to open a compatible urn
                    ItemStack urn = UrnHelper.selectSwapperUrnToOpen(serverPlayer);
                    if (!urn.isEmpty()) {
                        serverPlayer.openMenu(
                                new SimpleMenuProvider(
                                        (id, inv, p) -> new LoginarUrnSwapperMenu(id, inv, urn),
                                        urn.getHoverName()
                                ),
                                buf -> buf.writeItem(urn)
                        );
                    } else {
                        serverPlayer.sendSystemMessage(TextUtil.misc("swapper.noCompatibleUrn"));
                    }
                }
            });
        });
    }

    public void handleSwapItemFromUrn(CPacketSwapItemFromUrn data, PlayPayloadContext ctx) {
        // Player selected an item from the urn item swapper menu. Swap the item with the held item.
        handleData(ctx, () -> ctx.player().ifPresent(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                ItemStack urn = UrnHelper.selectSwapperUrnToOpen(serverPlayer);
                if (!urn.isEmpty()) {
                    UrnData urnData = UrnData.fromItem(urn);

                    ItemStack currentHeldItem = serverPlayer.getMainHandItem();
                    ItemStack swapItem = urnData.items().get(data.urnItemSlot());
                    player.setItemInHand(InteractionHand.MAIN_HAND, swapItem);
                    urnData.items().set(data.urnItemSlot(), currentHeldItem);

                    UrnHelper.saveAllItems(urn.getOrCreateTag().getCompound(UrnData.NBT_ROOT), UrnData.NBT_ITEMS, urnData.items(), false);
                }
            }
        }));
    }
}
