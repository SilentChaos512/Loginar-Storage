package net.silentchaos512.loginar;

import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.silentchaos512.loginar.setup.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(LoginarMod.MOD_ID)
public final class LoginarMod {
    public static final String MOD_ID = "loginar";
    public static final String MOD_NAME = "Loginar Storage";

    public static final Random RANDOM = new Random();
    public static final RandomSource RANDOM_SOURCE = RandomSource.create();
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static LoginarMod INSTANCE;

    public LoginarMod(IEventBus modEventBus) {
        INSTANCE = this;

        LsBlockEntityTypes.REGISTER.register(modEventBus);
        LsBlocks.REGISTER.register(modEventBus);
        LsDataComponents.REGISTRAR.register(modEventBus);
        LsEntityTypes.REGISTER.register(modEventBus);
        LsItems.ITEMS.register(modEventBus);
        modEventBus.addListener(LsItems::onBuildContentsOfCreativeTabs);
        LsMenuTypes.REGISTER.register(modEventBus);
        LsPlacementModifierTypes.REGISTER.register(modEventBus);
        LsRecipeSerializers.REGISTER.register(modEventBus);
        LsSounds.REGISTER.register(modEventBus);
    }

    public static Identifier getId(String path) {
        if (path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}