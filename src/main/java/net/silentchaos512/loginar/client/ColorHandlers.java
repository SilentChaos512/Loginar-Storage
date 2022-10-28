package net.silentchaos512.loginar.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.setup.LsBlocks;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ColorHandlers {
    private ColorHandlers() {}

    @SubscribeEvent
    public static void onBlockColors(ColorHandlerEvent.Block event) {
        event.getBlockColors().register(LoginarUrnBlock::getBlockColor, LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));
    }

    @SubscribeEvent
    public static void onItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register(LoginarUrnBlock::getItemColor, LsBlocks.getUrns().toArray(new LoginarUrnBlock[0]));
    }
}
