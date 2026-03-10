package net.silentchaos512.loginar.client.setup;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.client.color.item.NextPotionInPouch;
import net.silentchaos512.loginar.client.color.item.UrnClayColor;
import net.silentchaos512.loginar.client.color.item.UrnGemColor;

@EventBusSubscriber(value = Dist.CLIENT)
public class LsItemTintSources {
    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(LoginarMod.getId("urn_clay_color"), UrnClayColor.CODEC);
        event.register(LoginarMod.getId("urn_gem_color"), UrnGemColor.CODEC);
        event.register(LoginarMod.getId("next_potion_in_pouch"), NextPotionInPouch.CODEC);
    }

    public static UrnClayColor urnClayColor() {
        return new UrnClayColor(UrnHelper.DEFAULT_CLAY_COLOR);
    }

    public static UrnGemColor urnGemColor() {
        return new UrnGemColor(UrnHelper.DEFAULT_GEM_COLOR);
    }

    public static NextPotionInPouch nextPotionInPouch() {
        return new NextPotionInPouch();
    }
}
