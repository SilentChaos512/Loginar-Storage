package net.silentchaos512.loginar.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.setup.LsBlocks;

@EventBusSubscriber(value = Dist.CLIENT)
public final class ColorHandlers {
    private ColorHandlers() {}

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(LoginarUrnBlock::getBlockColor, LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));
    }
}
