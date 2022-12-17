package net.silentchaos512.loginar.setup;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBackpackMenu;
import net.silentchaos512.loginar.block.urn.LoginarUrnMenu;
import net.silentchaos512.loginar.block.urn.LoginarUrnSwapperMenu;

public class LsMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, LoginarMod.MOD_ID);

    public static final RegistryObject<MenuType<LoginarUrnMenu>> LOGINAR_URN = register("loginar_urn",
            LoginarUrnMenu::new
    );
    public static final RegistryObject<MenuType<LoginarUrnBackpackMenu>> LOGINAR_URN_BACKPACK = register("loginar_urn_backpack",
            LoginarUrnBackpackMenu::new
    );
    public static final RegistryObject<MenuType<LoginarUrnSwapperMenu>> LOGINAR_URN_SWAPPER = register("loginar_urn_swapper",
            LoginarUrnSwapperMenu::new
    );

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, IContainerFactory<T> factory) {
        return REGISTER.register(name, () -> IForgeMenuType.create(factory));
    }
}
