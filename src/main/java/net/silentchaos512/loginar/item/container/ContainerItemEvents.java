package net.silentchaos512.loginar.item.container;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.TriState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

@EventBusSubscriber
public class ContainerItemEvents {
    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event) {
        if (event.getItemEntity().hasPickUpDelay()) return;

        ItemStack itemOnGround = event.getItemEntity().getItem();
        int initialCount = itemOnGround.getCount();
        Player player = event.getPlayer();

        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof IContainerItem containerItem && containerItem.canPickup(itemOnGround)) {
                var itemHandler = containerItem.getItemHandler(stack);
                try (var tx = Transaction.openRoot()) {
                    int amountInserted = itemHandler.insert(ItemResource.of(itemOnGround), itemOnGround.getCount(), tx);
                    if (amountInserted > 0) {
                        itemOnGround.shrink(amountInserted);
                        tx.commit();
                    }
                }

                if (itemOnGround.isEmpty()) {
                    event.setCanPickup(TriState.TRUE);
                    event.getItemEntity().remove(Entity.RemovalReason.DISCARDED);
                    break;
                }
            }
        }

        if (itemOnGround.getCount() != initialCount) {
            var level = player.level();
            var random = level.getRandom();
            float pitch = ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F;
            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(),
                    SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, pitch);
        }
    }
}
