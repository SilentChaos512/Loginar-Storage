package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.lib.registry.ItemRegistryObject;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.item.*;

import java.util.function.Supplier;

public class LsItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, LoginarMod.MOD_ID);

    // Loginar drops
    public static final ItemRegistryObject<LoginarAntennaItem> LOGINAR_ANTENNA = register("loginar_antenna", () ->
            new LoginarAntennaItem(props())
    );
    public static final ItemRegistryObject<LoginarTentacleItem> LOGINAR_TENTACLE = register("loginar_tentacle", () ->
            new LoginarTentacleItem(props()
                    .food(LsFoods.LOGINAR_TENTACLE)
            )
    );
    public static final ItemRegistryObject<Item> LOGINAR_CALAMARI = register("loginar_calamari", () ->
            new Item(props()
                    .food(LsFoods.LOGINAR_CALAMARI)
            )
    );

    // Urn upgrades
    public static final ItemRegistryObject<UpgradeItem> BACKPACK_UPGRADE = register("backpack_upgrade", () ->
            new UpgradeItem(props())
    );
    public static final ItemRegistryObject<UpgradeItem> VACUUM_UPGRADE = register("vacuum_upgrade", () ->
            new VacuumUrnUpgrade(props())
    );
    public static final ItemRegistryObject<UpgradeItem> ITEM_SWAPPER_UPGRADE = register("item_swapper_upgrade", () ->
            new UpgradeItem(props())
    );

    // Container items
    public static final ItemRegistryObject<LunchBoxItem> LUNCH_BOX = register("lunch_box", () ->
            new LunchBoxItem(props().stacksTo(1).setNoRepair())
    );
    public static final ItemRegistryObject<GemBagItem> GEM_BAG = register("gem_bag", () ->
            new GemBagItem(props().stacksTo(1).setNoRepair())
    );
    public static final ItemRegistryObject<FlowerBasketItem> FLOWER_BASKET = register("flower_basket", () ->
            new FlowerBasketItem(props().stacksTo(1).setNoRepair())
    );
    public static final ItemRegistryObject<OreCrateItem> ORE_CRATE = register("ore_crate", () ->
            new OreCrateItem(props().stacksTo(1).setNoRepair())
    );

    // Misc
    public static final ItemRegistryObject<ForgeSpawnEggItem> LOGINAR_SPAWN_EGG = register("loginar_spawn_egg", () ->
            new ForgeSpawnEggItem(LsEntityTypes.LOGINAR, 0x59B9FF, 0xFFFFFF, props()));

    protected static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> item) {
        return new ItemRegistryObject<>(REGISTER.register(name, item));
    }

    private static Item.Properties props() {
        return new Item.Properties();
    }

    public static void onBuildContentsOfCreativeTabs(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(LsBlocks.TINY_LOGINAR_URN.get());
            event.accept(LsBlocks.SMALL_LOGINAR_URN.get());
            event.accept(LsBlocks.MEDIUM_LOGINAR_URN.get());
            event.accept(LsBlocks.LARGE_LOGINAR_URN.get());
            event.accept(LsBlocks.HUGE_LOGINAR_URN.get());
            event.accept(LsBlocks.SUPER_LOGINAR_URN.get());
        }
        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(LUNCH_BOX.get());
            event.accept(LOGINAR_TENTACLE.get());
            event.accept(LOGINAR_CALAMARI.get());
        }
        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(LOGINAR_ANTENNA.get());
        }
        if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BACKPACK_UPGRADE.get());
            event.accept(VACUUM_UPGRADE.get());
            event.accept(ITEM_SWAPPER_UPGRADE.get());
            event.accept(GEM_BAG.get());
            event.accept(FLOWER_BASKET.get());
            event.accept(ORE_CRATE.get());
        }
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(LOGINAR_SPAWN_EGG.get());
        }
    }
}
