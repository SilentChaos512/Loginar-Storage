package net.silentchaos512.loginar.client;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.item.PotionPouchItem;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class ColorHandlers {
    private ColorHandlers() {}

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(LoginarUrnBlock::getBlockColor, LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));
    }

    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(LoginarUrnBlock::getItemColor, LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));

        event.register(
                (stack, tintIndex) -> {
                    if (tintIndex == 1 && stack.getItem() instanceof PotionPouchItem potionPouchItem) {
                        var potionStack = potionPouchItem.getNextPotion(stack);
                        return potionStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
                    }
                    return -1;
                },
                LsItems.POTION_POUCH
        );
    }
}
