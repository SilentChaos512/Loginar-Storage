package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.LanguageProvider;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsEntityTypes;
import net.silentchaos512.loginar.setup.LsItems;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen, LoginarMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Blocks
        addBlock(LsBlocks.TINY_LOGINAR_URN, "Tiny Loginar Urn");
        addBlock(LsBlocks.SMALL_LOGINAR_URN, "Small Loginar Urn");
        addBlock(LsBlocks.MEDIUM_LOGINAR_URN, "Medium Loginar Urn");
        addBlock(LsBlocks.LARGE_LOGINAR_URN, "Large Loginar Urn");
        addBlock(LsBlocks.HUGE_LOGINAR_URN, "Huge Loginar Urn");
        addBlock(LsBlocks.SUPER_LOGINAR_URN, "Super Loginar Urn");

        // Containers
        add("container", "loginar_urn", "Loginar Urn");

        // Entities
        addEntityType(LsEntityTypes.LOGINAR, "Loginar");

        // Keybindings
        add("key.category." + LoginarMod.MOD_ID, "Loginar Storage");
        add("key", "swapUrnItems", "Swap Urn Items");

        // Items
        addItem(LsItems.LOGINAR_ANTENNA, "Loginar Antenna");
        addItemSub(LsItems.LOGINAR_ANTENNA, "lit", "The antenna is shining faintly...");
        addItem(LsItems.LOGINAR_TENTACLE, "Loginar Tentacle");
        addItem(LsItems.LOGINAR_CALAMARI, "Loginar Calamari");
        addItem(LsItems.LOGINAR_SPAWN_EGG, "Loginar Spawn Egg");
        // Urn Upgrades
        addItem(LsItems.BACKPACK_UPGRADE, "Backpack Urn Upgrade");
        addItemSub(LsItems.BACKPACK_UPGRADE, "desc", "Allows the urn to be opened without placing it");
        addItem(LsItems.VACUUM_UPGRADE, "Vacuum Urn Upgrade");
        addItemSub(LsItems.VACUUM_UPGRADE, "desc", "Draws in nearby items and stores them");
        addItem(LsItems.ITEM_SWAPPER_UPGRADE, "Item Swapper Urn Upgrade");
        addItemSub(LsItems.ITEM_SWAPPER_UPGRADE, "desc", "Allows individual items to be swapped out of the urn by pressing a bound key");

        // Misc
        add("misc", "not_implemented", "Not Implemented! This feature does not work yet. :(");
        add("misc", "swapper.holdingUrn", "Cannot swap items with an urn");
        add("misc", "swapper.noCompatibleUrn", "No urns with swapper upgrades found");
        add("misc", "urn.upgrades", "Upgrades (%s / %s)");

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
}
