package net.silentchaos512.loginar.network;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockItem;
import net.silentchaos512.loginar.block.urn.LoginarUrnSwapperMenu;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.util.TextUtil;

public class LsServerPayloadHandler {
    private static final LsServerPayloadHandler INSTANCE = new LsServerPayloadHandler();

    public static LsServerPayloadHandler getInstance() {
        return INSTANCE;
    }

    private static void handleData(final IPayloadContext ctx, Runnable handler) {
        ctx.enqueueWork(handler)
                .exceptionally(e -> {
                    ctx.disconnect(Component.translatable("network.loginar.failure", e.getMessage()));
                    return null;
                });
    }

    public void handleOpenUrnForItemSwap(OpenUrnForItemSwapPayload data, IPayloadContext ctx) {
        // Player pressed the item swap key. Search for an urn with a supported item swapper upgrade and open a menu.
        handleData(ctx, () -> {
            var player = ctx.player();
            if (player instanceof ServerPlayer serverPlayer) {
                ItemStack mainHandItem = serverPlayer.getMainHandItem();
                if (!UrnHelper.canUrnStore(mainHandItem)) {
                    serverPlayer.sendSystemMessage(TextUtil.misc("swapper.cannotStore", mainHandItem.getDisplayName()));
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
                            buf -> ItemStack.STREAM_CODEC.encode(buf, urn)
                    );
                } else {
                    serverPlayer.sendSystemMessage(TextUtil.misc("swapper.noCompatibleUrn"));
                }
            }
        });
    }

    public void handleSwapItemFromUrn(SwapItemFromUrnPayload data, IPayloadContext ctx) {
        // Player selected an item from the urn item swapper menu. Swap the item with the held item.
        handleData(ctx, () -> {
            var player = ctx.player();
            if (player instanceof ServerPlayer serverPlayer) {
                ItemStack urn = UrnHelper.selectSwapperUrnToOpen(serverPlayer);
                if (!urn.isEmpty()) {
                    NonNullList<ItemStack> items = UrnHelper.getItemsMutableCopy(urn);
                    ItemStack currentHeldItem = serverPlayer.getMainHandItem();
                    ItemStack swapItem = items.get(data.urnItemSlot());

                    player.setItemInHand(InteractionHand.MAIN_HAND, swapItem);
                    items.set(data.urnItemSlot(), currentHeldItem);
                    urn.set(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.fromItems(items));
                }
            }
        });
    }

    public void handleOpenBackpackUrn(OpenBackpackUrnPayload data, IPayloadContext ctx) {
        handleData(ctx, () -> {
            var player = ctx.player();
            if (player instanceof ServerPlayer serverPlayer) {
                ItemStack urn = UrnHelper.selectBackpackUrnToOpen(serverPlayer);
                if (!urn.isEmpty() && urn.getItem() instanceof LoginarUrnBlockItem blockItem) {
                    blockItem.openContainer(serverPlayer, urn);
                }
            }
        });
    }
}
