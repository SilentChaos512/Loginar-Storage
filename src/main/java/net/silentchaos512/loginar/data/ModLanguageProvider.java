package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen, LoginarMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Blocks
        addBlock(LsBlocks.TINY_LOGINAR_URN, "Loginar Urn (Tiny)");
        addBlock(LsBlocks.SMALL_LOGINAR_URN, "Loginar Urn (Small)");
        addBlock(LsBlocks.MEDIUM_LOGINAR_URN, "Loginar Urn (Medium)");
        addBlock(LsBlocks.LARGE_LOGINAR_URN, "Loginar Urn (Large)");
        addBlock(LsBlocks.HUGE_LOGINAR_URN, "Loginar Urn (Huge)");
        addBlock(LsBlocks.SUPER_LOGINAR_URN, "Loginar Urn (Super)");

        // Containers
        add("container", "loginar_urn", "Loginar Urn");

        // Items
        addItem(LsItems.LOGINAR_ANTENNA, "Loginar Antenna");
        add("item.loginar.loginar_antenna.lit", "The antenna is shining faintly...");
        addItem(LsItems.LOGINAR_TENTACLE, "Loginar Tentacle");
        addItem(LsItems.LOGINAR_CALAMARI, "Loginar Calamari");
        addItem(LsItems.LOGINAR_SPAWN_EGG, "Loginar Spawn Egg");
    }

    private void add(String prefix, String suffix, String value) {
        add(prefix + "." + LoginarMod.MOD_ID + "." + suffix, value);
    }
}
