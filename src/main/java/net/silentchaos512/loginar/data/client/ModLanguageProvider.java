package net.silentchaos512.loginar.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsEntityTypes;
import net.silentchaos512.loginar.setup.LsItems;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen.getPackOutput(), LoginarMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Advancements
        advancement("root", "Loginar Storage", "Obtain a loginar antenna");
        advancement("tiny_loginar_urn", "A New Pocket", "Craft a tiny loginar urn");
        advancement("small_loginar_urn", "Small and Cute", "Craft a small loginar urn");
        advancement("medium_loginar_urn", "A Portable Chest", "Craft a medium loginar urn");
        advancement("large_loginar_urn", "Overachiever", "Craft a large loginar urn");
        advancement("huge_loginar_urn", "Dragon Sized", "Craft a huge loginar urn");
        advancement("super_loginar_urn", "Davy Jones' Locker", "Craft a super loginar urn");
        advancement("loginar_dungeon", "Breaking and Entering", "Find a loginar dungeon");
        advancement("loginar_calamari", "Squidward, Was That You?", "Eat some loginar calamari");

        // Blocks
        addBlock(LsBlocks.TINY_LOGINAR_URN, "Tiny Loginar Urn");
        addBlock(LsBlocks.SMALL_LOGINAR_URN, "Small Loginar Urn");
        addBlock(LsBlocks.MEDIUM_LOGINAR_URN, "Medium Loginar Urn");
        addBlock(LsBlocks.LARGE_LOGINAR_URN, "Large Loginar Urn");
        addBlock(LsBlocks.HUGE_LOGINAR_URN, "Huge Loginar Urn");
        addBlock(LsBlocks.SUPER_LOGINAR_URN, "Super Loginar Urn");
        addBlock(LsBlocks.LOGINAR_EGG, "Loginar Egg");

        // Containers
        add("container", "loginar_urn", "Loginar Urn");
        add("container", "lunch_box", "Lunch Box");
        add("container", "potion_pouch", "Potion Pouch");
        add("container", "gem_bag", "Gem Bag");
        add("container", "flower_basket", "Flower Basket");
        add("container", "ore_crate", "Ore Crate");
        add("container", "seed_bag", "Seed Bag");
        add("container", "wood_rack", "Wood Rack");

        // Entities
        addEntityType(LsEntityTypes.LOGINAR, "Loginar");
        addEntityType(LsEntityTypes.FRIENDLY_LOGINAR, "Friendly Loginar");

        // Keybindings
        add("key.category." + LoginarMod.MOD_ID, "Loginar Storage");
        add("key", "openBackpack", "Open Backpack Urn");
        add("key", "swapUrnItems", "Swap Item Into Swapper Urn");

        // Items
        addItem(LsItems.LOGINAR_ANTENNA, "Loginar Antenna");
        addItemSub(LsItems.LOGINAR_ANTENNA, "lit", "The antenna is shining faintly...");
        addItem(LsItems.LOGINAR_TENTACLE, "Loginar Tentacle");
        addItem(LsItems.LOGINAR_CALAMARI, "Loginar Calamari");
        addItem(LsItems.LOGINAR_SPAWN_EGG, "Loginar Spawn Egg");
        addItem(LsItems.FRIENDLY_LOGINAR_SPAWN_EGG, "Friendly Loginar Spawn Egg");
        addItem(LsItems.FIRE_PEARL, "Fire Pearl");
        addItem(LsItems.FIRE_FLINGER, "Fire Flinger");
        // Urn Upgrades
        addItem(LsItems.BACKPACK_UPGRADE, "Backpack Urn Upgrade");
        addItemSub(LsItems.BACKPACK_UPGRADE, "desc", "Allows the urn to be opened without placing it");
        addItem(LsItems.VACUUM_UPGRADE, "Vacuum Urn Upgrade");
        addItemSub(LsItems.VACUUM_UPGRADE, "desc", "Draws in nearby items and stores them");
        addItem(LsItems.ITEM_SWAPPER_UPGRADE, "Item Swapper Urn Upgrade");
        addItemSub(LsItems.ITEM_SWAPPER_UPGRADE, "desc", "Allows individual items to be swapped out of the urn by pressing a bound key");
        addItem(LsItems.SUPPLIER_UPGRADE, "Supplier Urn Upgrade");
        addItemSub(LsItems.SUPPLIER_UPGRADE, "desc", "Replenishes consumed items (blocks, tools, etc.) when a stack is used up");
        // Container items
        addItem(LsItems.FLOWER_BASKET, "Flower Basket");
        addItemSub(LsItems.FLOWER_BASKET, "desc", "Stores and picks up flowers");
        addItem(LsItems.GEM_BAG, "Gem Bag");
        addItemSub(LsItems.GEM_BAG, "desc", "Stores and picks up gems");
        addItem(LsItems.LUNCH_BOX, "Lunch Box");
        addItemSub(LsItems.LUNCH_BOX, "desc", "Stores food. Use to eat, sneak + use to open.");
        addItemSub(LsItems.LUNCH_BOX, "next_food", "Next food in lunch box: %s");
        addItem(LsItems.ORE_CRATE, "Ore Crate");
        addItemSub(LsItems.ORE_CRATE, "desc", "Stores and picks up raw ores");
        addItem(LsItems.POTION_POUCH, "Potion Pouch");
        addItemSub(LsItems.POTION_POUCH, "desc", "Stores potions and lets you drink or throw them. Sneak + use to open.");
        addItem(LsItems.SEED_BAG, "Seed bag");
        addItemSub(LsItems.SEED_BAG, "desc", "Stores and picks up seeds. Can be used to plant them as well.");
        addItem(LsItems.WOOD_RACK, "Wood Rack");
        addItemSub(LsItems.WOOD_RACK, "desc", "Stores and picks up logs and sticks");

        // Misc
        add("misc", "not_implemented", "Not Implemented! This feature does not work yet. :(");
        add("misc", "swapper.cannotStore", "Cannot store %s in a loginar urn");
        add("misc", "swapper.noCompatibleUrn", "No urns with swapper upgrades found");
        add("misc", "urn.upgrades", "Upgrades (%s / %s)");
        add("misc", "urn.clayColor", "Clay Color: %s");
        add("misc", "urn.gemColor", "Gem Color: %s");

        // Subtitles
        add("subtitles.block.loginar.urn.lid", "Loginar urn lid moves");
        add("subtitles.block.loginar.urn.open", "Loginar urn opens");
        add("subtitles.entity.loginar.attack", "Loginar barks");
        add("subtitles.entity.loginar.death", "Loginar dies");
        add("subtitles.entity.loginar.hurt", "Loginar hurt");
        add("subtitles.entity.loginar.idle", "Loginar purrs");
    }

    private void add(String prefix, String suffix, String value) {
        add(prefix + "." + LoginarMod.MOD_ID + "." + suffix, value);
    }

    private void addItemSub(ItemLike item, String suffix, String value) {
        ResourceLocation name = NameUtils.fromItem(item);
        add(String.format("item.%s.%s.%s", name.getNamespace(), name.getPath(), suffix), value);
    }

    private void advancement(String key, String title, String description) {
        add("advancement.loginar." + key + ".title", title);
        add("advancement.loginar." + key + ".description", description);
    }
}
