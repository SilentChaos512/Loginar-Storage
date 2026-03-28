package net.silentchaos512.loginar.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.level.placement.LoginarChunkFilter;
import net.silentchaos512.loginar.setup.LsBlocks;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenGenerator extends DatapackBuiltinEntriesProvider {
    @SuppressWarnings("Convert2MethodRef")
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ctx -> {
                ctx.register(featureKey("fire_flower"), fireFlower());
            })
            .add(Registries.PLACED_FEATURE, ctx -> {
                placedFireFlower(ctx);
            })
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ctx -> {
                ctx.register(
                        biomeModifierKey("overworld"),
                        new BiomeModifiers.AddFeaturesBiomeModifier(
                                ctx.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
                                HolderSet.direct(ctx.lookup(Registries.PLACED_FEATURE).getOrThrow(placedKey("fire_flower"))),
                                GenerationStep.Decoration.VEGETAL_DECORATION
                        )
                );
            });

    public ModWorldGenGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Collections.singleton(LoginarMod.MOD_ID));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> featureKey(String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, LoginarMod.getId(path));
    }

    private static ResourceKey<PlacedFeature> placedKey(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE, LoginarMod.getId(path));
    }

    protected static ResourceKey<BiomeModifier> biomeModifierKey(String path) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, LoginarMod.getId(path));
    }

    private static ConfiguredFeature<SimpleBlockConfiguration, Feature<SimpleBlockConfiguration>> fireFlower() {
        return new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(LsBlocks.FIRE_FLOWER.get()))
        );
    }

    private static void placedFireFlower(BootstrapContext<PlacedFeature> ctx) {
        ResourceKey<ConfiguredFeature<?, ?>> key = featureKey("fire_flower");
        // The intention is to only spawn flowers in loginar chunks, but they seem to spawn in other chunks too
        PlacedFeature placed = new PlacedFeature(
                ctx.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(key),
                List.of(
                        LoginarChunkFilter.INSTANCE,
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        BiomeFilter.biome(),
                        CountPlacement.of(4),
                        RandomOffsetPlacement.ofTriangle(7, 3),
                        BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
                )
        );
        ctx.register(placedKey("fire_flower"), placed);
    }
}
