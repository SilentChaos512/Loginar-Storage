package net.silentchaos512.loginar.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(event);
        gen.addProvider(true, blockTags);
        gen.addProvider(true, new ModItemTagsProvider(event, blockTags));

        gen.addProvider(true, new ModLootTableProvider(event));
        gen.addProvider(true, new ModRecipeProvider(event));

        gen.addProvider(true, new ModLanguageProvider(gen));
        gen.addProvider(true, new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModSoundDefinitionsProvider(gen, existingFileHelper));

        gen.addProvider(true, new ModAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
    }
}
