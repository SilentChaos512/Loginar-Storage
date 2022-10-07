package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LoginarItems;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen, LoginarMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItem(LoginarItems.LOGINAR_ANTENNA, "Loginar Antenna");
        addItem(LoginarItems.LOGINAR_TENTACLE, "Loginar Tentacle");
        addItem(LoginarItems.LOGINAR_CALAMARI, "Loginar Calamari");
    }
}
