package net.silentchaos512.loginar.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.util.Const;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LsItemModelProperties {
    private LsItemModelProperties() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(
                    LsItems.LOGINAR_ANTENNA.get(),
                    Const.IS_LOGINAR_CHUNK,
                    (stack, level, entity, par4) -> {
                        // TODO: Return 1 for loginar spawn chunks, 0 otherwise (can only calculate on the server...)
                        return LoginarMod.RANDOM.nextInt(20) == 0 ? 1 : 0;
                    }
            );
        });

        event.enqueueWork(() -> {
            LsItems.REGISTER.getEntries().stream()
                    .filter(holder -> holder.get() instanceof ContainerItem)
                    .map(DeferredHolder::get)
                    .forEach(item -> ItemProperties.register(
                            item,
                            Const.FILLED,
                            (stack, level, entity, seed) -> ContainerItem.containsAnyItems(stack) ? 1 : 0
                    ));
        });
    }
}
