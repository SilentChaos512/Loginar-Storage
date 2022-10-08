package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LuBlocks;
import net.silentchaos512.loginar.setup.LuItems;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen, LoginarMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Blocks
        addBlock(LuBlocks.LOGINAR_URN, "Loginar Urn");

        // Containers
        add("container", "loginar_urn", "Loginar Urn");

        // Items
        addItem(LuItems.LOGINAR_ANTENNA, "Loginar Antenna");
        addItem(LuItems.LOGINAR_TENTACLE, "Loginar Tentacle");
        addItem(LuItems.LOGINAR_CALAMARI, "Loginar Calamari");
    }

    private void add(String prefix, String suffix, String value) {
        add(prefix + "." + LoginarMod.MOD_ID + "." + suffix, value);
    }
}
