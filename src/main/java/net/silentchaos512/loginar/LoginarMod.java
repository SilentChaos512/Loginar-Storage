package net.silentchaos512.loginar;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silentchaos512.loginar.network.LsNetwork;
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

    public LoginarMod() {
        INSTANCE = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LsBlockEntityTypes.REGISTER.register(modEventBus);
        LsBlocks.REGISTER.register(modEventBus);
        LsEntityTypes.REGISTER.register(modEventBus);
        LsItems.REGISTER.register(modEventBus);
        modEventBus.addListener(LsItems::onBuildContentsOfCreativeTabs);
        LsMenuTypes.REGISTER.register(modEventBus);
        LsRecipeSerializers.REGISTER.register(modEventBus);
        LsSounds.REGISTER.register(modEventBus);

        LsNetwork.init();
    }

    public static ResourceLocation getId(String path) {
        if (path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return new ResourceLocation(MOD_ID, path);
    }
}