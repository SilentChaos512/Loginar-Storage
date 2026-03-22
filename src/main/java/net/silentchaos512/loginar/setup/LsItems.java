package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.item.*;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class LsItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LoginarMod.MOD_ID);

    // Loginar drops
    public static final DeferredItem<LoginarAntennaItem> LOGINAR_ANTENNA = register(
            "loginar_antenna",
            LoginarAntennaItem::new
    );
    public static final DeferredItem<LoginarTentacleItem> LOGINAR_TENTACLE = register(
            "loginar_tentacle",
            LoginarTentacleItem::new,
            properties -> properties
                    .food(LsFoods.LOGINAR_TENTACLE)
    );
    public static final DeferredItem<Item> LOGINAR_CALAMARI = register(
            "loginar_calamari",
            Item::new,
            properties -> properties
                    .food(LsFoods.LOGINAR_CALAMARI)
    );
    public static final DeferredItem<Item> FIRE_PEARL = registerSimpleItem("fire_pearl");

    // Crafting items
    public static final DeferredItem<Item> TINY_CRYSTAL = registerSimpleItem("tiny_crystal");
    public static final DeferredItem<Item> SMALL_CRYSTAL = registerSimpleItem("small_crystal");
    public static final DeferredItem<Item> MEDIUM_CRYSTAL = registerSimpleItem("medium_crystal");
    public static final DeferredItem<Item> LARGE_CRYSTAL = registerSimpleItem("large_crystal");
    public static final DeferredItem<Item> HUGE_CRYSTAL = registerSimpleItem("huge_crystal");
    public static final DeferredItem<Item> SUPER_CRYSTAL = registerSimpleItem("super_crystal");

    // Urn upgrades
    public static final DeferredItem<UpgradeItem> BACKPACK_UPGRADE = register(
            "backpack_upgrade",
            UpgradeItem::new
    );
    public static final DeferredItem<UpgradeItem> VACUUM_UPGRADE = register(
            "vacuum_upgrade",
            VacuumUrnUpgrade::new
    );
    public static final DeferredItem<UpgradeItem> ITEM_SWAPPER_UPGRADE = register(
            "item_swapper_upgrade",
            UpgradeItem::new
    );
    public static final DeferredItem<UpgradeItem> SUPPLIER_UPGRADE = register(
            "supplier_upgrade",
            UpgradeItem::new
    );

    // Container items
    public static final DeferredItem<LunchBoxItem> LUNCH_BOX = register(
            "lunch_box",
            LunchBoxItem::new,
            LsItems::unstackable
    );
    public static final DeferredItem<PotionPouchItem> POTION_POUCH = register(
            "potion_pouch",
            PotionPouchItem::new,
            LsItems::unstackable
    );
    public static final DeferredItem<SeedBagItem> SEED_BAG = register(
            "seed_bag",
            SeedBagItem::new,
            LsItems::unstackable
    );
    public static final DeferredItem<FlowerBasketItem> FLOWER_BASKET = register(
            "flower_basket",
            FlowerBasketItem::new,
            LsItems::unstackable
    );
    public static final DeferredItem<WoodRackItem> WOOD_RACK = register(
            "wood_rack",
            WoodRackItem::new,
            LsItems::unstackable
    );
    public static final DeferredItem<GemBagItem> GEM_BAG = register(
            "gem_bag",
            GemBagItem::new,
            LsItems::unstackable
    );
    public static final DeferredItem<OreCrateItem> ORE_CRATE = register(
            "ore_crate",
            OreCrateItem::new,
            LsItems::unstackable
    );

    // Misc
    public static final DeferredItem<FireFlingerItem> FIRE_FLINGER = register(
            "fire_flinger",
            FireFlingerItem::new,
            properties -> properties
                    .durability(64)
                    .stacksTo(1)
    );
    public static final DeferredItem<SpawnEggItem> LOGINAR_SPAWN_EGG = register(
            "loginar_spawn_egg",
            SpawnEggItem::new,
            properties -> properties
                    .spawnEgg(LsEntityTypes.LOGINAR.get())
    );
    public static final DeferredItem<SpawnEggItem> FRIENDLY_LOGINAR_SPAWN_EGG = register(
            "friendly_loginar_spawn_egg",
            SpawnEggItem::new,
            properties -> properties
                    .spawnEgg(LsEntityTypes.FRIENDLY_LOGINAR.get())
    );

    static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item) {
        return ITEMS.registerItem(name, item);
    }

    static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item, UnaryOperator<Item.Properties> properties) {
        return ITEMS.registerItem(name, item, properties);
    }

    static DeferredItem<Item> registerSimpleItem(String name) {
        return ITEMS.registerItem(name, Item::new);
    }

    private static Item.Properties unstackable(Item.Properties properties) {
        return properties
                .stacksTo(1)
                .setNoCombineRepair();
    }

    public static void onBuildContentsOfCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(LsBlocks.TINY_LOGINAR_URN.get());
            event.accept(LsBlocks.SMALL_LOGINAR_URN.get());
            event.accept(LsBlocks.MEDIUM_LOGINAR_URN.get());
            event.accept(LsBlocks.LARGE_LOGINAR_URN.get());
            event.accept(LsBlocks.HUGE_LOGINAR_URN.get());
            event.accept(LsBlocks.SUPER_LOGINAR_URN.get());
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(LsBlocks.FIRE_FLOWER);
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(LUNCH_BOX.get());
            event.accept(POTION_POUCH.get());
            event.accept(LOGINAR_TENTACLE.get());
            event.accept(LOGINAR_CALAMARI.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(TINY_CRYSTAL.get());
            event.accept(SMALL_CRYSTAL.get());
            event.accept(MEDIUM_CRYSTAL.get());
            event.accept(LARGE_CRYSTAL.get());
            event.accept(HUGE_CRYSTAL.get());
            event.accept(SUPER_CRYSTAL.get());
            event.accept(LOGINAR_ANTENNA.get());
            event.accept(FIRE_PEARL.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BACKPACK_UPGRADE.get());
            event.accept(VACUUM_UPGRADE.get());
            event.accept(ITEM_SWAPPER_UPGRADE.get());
            event.accept(SUPPLIER_UPGRADE.get());
            event.accept(GEM_BAG.get());
            event.accept(FLOWER_BASKET.get());
            event.accept(ORE_CRATE.get());
            event.accept(SEED_BAG.get());
            event.accept(WOOD_RACK.get());
            event.accept(FIRE_FLINGER.get());
        }
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(LOGINAR_SPAWN_EGG.get());
            event.accept(FRIENDLY_LOGINAR_SPAWN_EGG.get());
        }
    }
}
