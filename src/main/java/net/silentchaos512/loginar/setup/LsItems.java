package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.item.*;

import java.util.function.Supplier;

public class LsItems {
    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(LoginarMod.MOD_ID);

    // Loginar drops
    public static final DeferredItem<LoginarAntennaItem> LOGINAR_ANTENNA = register("loginar_antenna", () ->
            new LoginarAntennaItem(props())
    );
    public static final DeferredItem<LoginarTentacleItem> LOGINAR_TENTACLE = register("loginar_tentacle", () ->
            new LoginarTentacleItem(props()
                    .food(LsFoods.LOGINAR_TENTACLE)
            )
    );
    public static final DeferredItem<Item> LOGINAR_CALAMARI = register("loginar_calamari", () ->
            new Item(props()
                    .food(LsFoods.LOGINAR_CALAMARI)
            )
    );
    public static final DeferredItem<Item> FIRE_PEARL = register("fire_pearl", () ->
            new Item(props())
    );

    // Urn upgrades
    public static final DeferredItem<UpgradeItem> BACKPACK_UPGRADE = register("backpack_upgrade", () ->
            new UpgradeItem(props())
    );
    public static final DeferredItem<UpgradeItem> VACUUM_UPGRADE = register("vacuum_upgrade", () ->
            new VacuumUrnUpgrade(props())
    );
    public static final DeferredItem<UpgradeItem> ITEM_SWAPPER_UPGRADE = register("item_swapper_upgrade", () ->
            new UpgradeItem(props())
    );
    public static final DeferredItem<UpgradeItem> SUPPLIER_UPGRADE = register("supplier_upgrade", () ->
            new UpgradeItem(props()));

    // Container items
    public static final DeferredItem<LunchBoxItem> LUNCH_BOX = register("lunch_box", () ->
            new LunchBoxItem(props().stacksTo(1).setNoCombineRepair())
    );
    public static final DeferredItem<PotionPouchItem> POTION_POUCH = register("potion_pouch", () ->
            new PotionPouchItem(props().stacksTo(1).setNoCombineRepair())
    );
    public static final DeferredItem<GemBagItem> GEM_BAG = register("gem_bag", () ->
            new GemBagItem(props().stacksTo(1).setNoCombineRepair())
    );
    public static final DeferredItem<FlowerBasketItem> FLOWER_BASKET = register("flower_basket", () ->
            new FlowerBasketItem(props().stacksTo(1).setNoCombineRepair())
    );
    public static final DeferredItem<OreCrateItem> ORE_CRATE = register("ore_crate", () ->
            new OreCrateItem(props().stacksTo(1).setNoCombineRepair())
    );
    public static final DeferredItem<SeedBagItem> SEED_BAG = register("seed_bag", () ->
            new SeedBagItem(props().stacksTo(1).setNoCombineRepair())
    );
    public static final DeferredItem<WoodRackItem> WOOD_RACK = register("wood_rack", () ->
            new WoodRackItem(props().stacksTo(1).setNoCombineRepair())
    );

    // Misc
    public static final DeferredItem<FireFlingerItem> FIRE_FLINGER = register("fire_flinger", () ->
            new FireFlingerItem(props().durability(64).stacksTo(1)));
    public static final DeferredItem<SpawnEggItem> LOGINAR_SPAWN_EGG = register("loginar_spawn_egg", () ->
            new SpawnEggItem(LsEntityTypes.LOGINAR.get(), props()));
    public static final DeferredItem<SpawnEggItem> FRIENDLY_LOGINAR_SPAWN_EGG = register("friendly_loginar_spawn_egg", () ->
            new SpawnEggItem(LsEntityTypes.FRIENDLY_LOGINAR.get(), props()));

    protected static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        return REGISTER.register(name, item);
    }

    private static Item.Properties props() {
        return new Item.Properties();
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
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(LUNCH_BOX.get());
            event.accept(POTION_POUCH.get());
            event.accept(LOGINAR_TENTACLE.get());
            event.accept(LOGINAR_CALAMARI.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
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
