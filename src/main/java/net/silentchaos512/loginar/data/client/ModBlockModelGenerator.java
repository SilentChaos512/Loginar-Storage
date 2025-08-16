package net.silentchaos512.loginar.data.client;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.resources.ResourceLocation;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.setup.LsBlocks;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModBlockModelGenerator extends BlockModelGenerators {
    public ModBlockModelGenerator(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        for (LoginarUrnBlock block : LsBlocks.getUrns()) {
            this.blockStateOutput.accept(
                    createSimpleBlock(block, plainVariant(id("block/" + NameUtils.fromBlock(block).getPath())))
            );
        }

        this.blockStateOutput.accept(
                createSimpleBlock(LsBlocks.LOGINAR_EGG.get(), plainVariant(id("block/loginar_egg")))
        );
    }

    private static ResourceLocation id(String path) {
        return LoginarMod.getId(path);
    }
}
