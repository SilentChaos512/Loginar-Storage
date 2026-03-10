package net.silentchaos512.loginar.client.setup;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.renderer.item.properties.ContainsItems;

@EventBusSubscriber
public class LsItemModelProperties {
    @SubscribeEvent
    public static void onRegisterConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(LoginarMod.getId("contains_items"), ContainsItems.CODEC);
    }
}
