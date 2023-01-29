package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.lib.registry.ItemRegistryObject;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.item.LoginarAntennaItem;
import net.silentchaos512.loginar.item.LoginarTentacleItem;
import net.silentchaos512.loginar.item.UpgradeItem;
import net.silentchaos512.loginar.item.VacuumUrnUpgrade;

import java.util.function.Supplier;

public class LsItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, LoginarMod.MOD_ID);

    // Loginar drops
    public static final ItemRegistryObject<LoginarAntennaItem> LOGINAR_ANTENNA = register("loginar_antenna", () ->
            new LoginarAntennaItem(props())
    );
    public static final ItemRegistryObject<LoginarTentacleItem> LOGINAR_TENTACLE = register("loginar_tentacle", () ->
            new LoginarTentacleItem(props()
                    .tab(CreativeModeTab.TAB_FOOD)
                    .food(LsFoods.LOGINAR_TENTACLE)
            )
    );
    public static final ItemRegistryObject<Item> LOGINAR_CALAMARI = register("loginar_calamari", () ->
            new Item(props()
                    .tab(CreativeModeTab.TAB_FOOD)
                    .food(LsFoods.LOGINAR_CALAMARI)
            )
    );

    // Urn upgrades
    public static final ItemRegistryObject<UpgradeItem> BACKPACK_UPGRADE = register("backpack_upgrade", () ->
            new UpgradeItem(false, props())
    );
    public static final ItemRegistryObject<UpgradeItem> VACUUM_UPGRADE = register("vacuum_upgrade", () ->
            new VacuumUrnUpgrade(props())
    );
    public static final ItemRegistryObject<UpgradeItem> ITEM_SWAPPER_UPGRADE = register("item_swapper_upgrade", () ->
            new UpgradeItem(false, props())
    );
    public static final ItemRegistryObject<UpgradeItem> HOTBAR_SWAPPER_UPGRADE = register("hotbar_swapper_upgrade", () ->
            new UpgradeItem(true, props())
    );

    // Misc
    public static final ItemRegistryObject<ForgeSpawnEggItem> LOGINAR_SPAWN_EGG = register("loginar_spawn_egg", () ->
            new ForgeSpawnEggItem(LsEntityTypes.LOGINAR, 0x59B9FF, 0xFFFFFF, props()));

    private static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> item) {
        return new ItemRegistryObject<>(REGISTER.register(name, item));
    }

    private static Item.Properties props() {
        return new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS);
    }
}
