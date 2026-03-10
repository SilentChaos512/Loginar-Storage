package net.silentchaos512.loginar.data.client;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.silentchaos512.lib.data.client.LibModelProvider;
import net.silentchaos512.loginar.LoginarMod;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModModelProvider extends LibModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, LoginarMod.MOD_ID);
    }

    @Nullable
    @Override
    protected BlockModelGenerators createBlockModelGenerators(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        return new ModBlockModelGenerator(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Nullable
    @Override
    protected ItemModelGenerators createItemModelGenerators(ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        return new ModItemModelProvider(itemModelOutput, modelOutput);
    }
}
