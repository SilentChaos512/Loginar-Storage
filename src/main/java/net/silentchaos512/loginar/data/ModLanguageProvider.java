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
        addBlock(LsBlocks.LOGINAR_URN, "Loginar Urn");

        // Containers
        add("container", "loginar_urn", "Loginar Urn");

        // Items
        addItem(LsItems.LOGINAR_ANTENNA, "Loginar Antenna");
        addItem(LsItems.LOGINAR_TENTACLE, "Loginar Tentacle");
        addItem(LsItems.LOGINAR_CALAMARI, "Loginar Calamari");
        addItem(LsItems.LOGINAR_SPAWN_EGG, "Loginar Spawn Egg");
    }

    private void add(String prefix, String suffix, String value) {
        add(prefix + "." + LoginarMod.MOD_ID + "." + suffix, value);
    }
}
