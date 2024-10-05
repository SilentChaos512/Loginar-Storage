package net.silentchaos512.loginar.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerDestroyItemEvent;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.setup.LsDataComponents;

@EventBusSubscriber
public class LsServerEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockPlace(PlayerDestroyItemEvent event) {
        // Supplier Urn Upgrade handler
        if (event.getEntity() instanceof ServerPlayer player && event.getHand() != null) {
            ItemStack supplierUrn = UrnHelper.selectSupplierUrn(player, event.getOriginal());
            if (!supplierUrn.isEmpty()) {
                // Resupply the item
                var items = UrnHelper.getItemsMutableCopy(supplierUrn);
                for (int i = 0; i < items.size(); i++) {
                    ItemStack stackInUrn = items.get(i);
                    if (ItemStack.isSameItem(stackInUrn, event.getOriginal())) {
                        player.setItemInHand(event.getHand(), stackInUrn);
                        items.set(i, ItemStack.EMPTY);
                        break;
                    }
                }
                supplierUrn.set(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.fromItems(items));
            }
        }
    }
}
