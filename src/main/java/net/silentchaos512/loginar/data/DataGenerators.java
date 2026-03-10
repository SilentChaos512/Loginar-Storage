package net.silentchaos512.loginar.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.silentchaos512.lib.data.recipe.LibRecipeProvider;
import net.silentchaos512.loginar.data.client.*;
import net.silentchaos512.loginar.data.tag.ModBlockTagsProvider;
import net.silentchaos512.loginar.data.tag.ModItemTagsProvider;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(packOutput, lookupProvider);
        gen.addProvider(true, blockTags);
        gen.addProvider(true, new ModItemTagsProvider(packOutput, lookupProvider));

        gen.addProvider(true, new ModLootTableProvider(event));
        gen.addProvider(true, LibRecipeProvider.createRunner(packOutput, lookupProvider, "Loginar Storage Recipes", ModRecipeProvider::new));

        gen.addProvider(true, new ModLanguageProvider(gen));
        gen.addProvider(true, new ModModelProvider(packOutput));
        gen.addProvider(true, new ModSoundDefinitionsProvider(gen));

        gen.addProvider(true, new ModAdvancementProvider(packOutput, lookupProvider));
    }
}
