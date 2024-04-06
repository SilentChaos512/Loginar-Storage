package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBackpackMenu;
import net.silentchaos512.loginar.block.urn.LoginarUrnMenu;
import net.silentchaos512.loginar.block.urn.LoginarUrnSwapperMenu;
import net.silentchaos512.loginar.item.FlowerBasketItem;
import net.silentchaos512.loginar.item.GemBagItem;
import net.silentchaos512.loginar.item.LunchBoxItem;
import net.silentchaos512.loginar.item.OreCrateItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;

public class LsMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(Registries.MENU, LoginarMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<LoginarUrnMenu>> LOGINAR_URN = register("loginar_urn",
            LoginarUrnMenu::new
    );
    public static final DeferredHolder<MenuType<?>, MenuType<LoginarUrnBackpackMenu>> LOGINAR_URN_BACKPACK = register("loginar_urn_backpack",
            LoginarUrnBackpackMenu::new
    );
    public static final DeferredHolder<MenuType<?>, MenuType<LoginarUrnSwapperMenu>> LOGINAR_URN_SWAPPER = register("loginar_urn_swapper",
            LoginarUrnSwapperMenu::new
    );

    public static final DeferredHolder<MenuType<?>, MenuType<ContainerItemMenu>> LUNCH_BOX = register("lunch_box", ((windowId, inv, data) ->
            new ContainerItemMenu(windowId, inv, LsMenuTypes.LUNCH_BOX.get(), LunchBoxItem.class)));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerItemMenu>> GEM_BAG = register("gem_bag", ((windowId, inv, data) ->
            new ContainerItemMenu(windowId, inv, LsMenuTypes.GEM_BAG.get(), GemBagItem.class)));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerItemMenu>> FLOWER_BASKET = register("flower_basket", ((windowId, inv, data) ->
            new ContainerItemMenu(windowId, inv, LsMenuTypes.FLOWER_BASKET.get(), FlowerBasketItem.class)));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerItemMenu>> ORE_CRATE = register("ore_crate", ((windowId, inv, data) ->
            new ContainerItemMenu(windowId, inv, LsMenuTypes.ORE_CRATE.get(), OreCrateItem.class)));

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String name, IContainerFactory<T> factory) {
        return REGISTER.register(name, () -> IMenuTypeExtension.create(factory));
    }
}
