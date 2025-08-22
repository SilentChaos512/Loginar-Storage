package net.silentchaos512.loginar.data.client;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.renderer.item.properties.ContainsItems;
import net.silentchaos512.loginar.client.setup.LsItemTintSources;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;

import java.util.function.BiConsumer;

public class ModItemModelProvider extends ItemModelGenerators {
    public ModItemModelProvider(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        generateLoginarUrn(LsBlocks.TINY_LOGINAR_URN);
        generateLoginarUrn(LsBlocks.SMALL_LOGINAR_URN);
        generateLoginarUrn(LsBlocks.MEDIUM_LOGINAR_URN);
        generateLoginarUrn(LsBlocks.LARGE_LOGINAR_URN);
        generateLoginarUrn(LsBlocks.HUGE_LOGINAR_URN);
        generateLoginarUrn(LsBlocks.SUPER_LOGINAR_URN);

        generateFlatItem(LsBlocks.LOGINAR_EGG.asItem(), ModelTemplates.FLAT_ITEM);

        generateFlatItem(LsItems.LOGINAR_ANTENNA.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.LOGINAR_TENTACLE.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.LOGINAR_CALAMARI.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.FIRE_PEARL.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.FIRE_FLINGER.get(), ModelTemplates.FLAT_ITEM);

        generateFlatItem(LsItems.BACKPACK_UPGRADE.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.VACUUM_UPGRADE.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.ITEM_SWAPPER_UPGRADE.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.SUPPLIER_UPGRADE.get(), ModelTemplates.FLAT_ITEM);

        generateFlatItem(LsItems.LUNCH_BOX.get(), ModelTemplates.FLAT_ITEM);
        generatePotionPouch(LsItems.POTION_POUCH.get(), "potion_pouch", "potion_pouch_empty", "potion_pouch_overlay");
        generateFlatItem(LsItems.GEM_BAG.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.FLOWER_BASKET.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.ORE_CRATE.get(), ModelTemplates.FLAT_ITEM);
        generatedFilledAndEmptyContainerItem(LsItems.SEED_BAG.get(), "seed_bag_filled", "seed_bag");
        generatedFilledAndEmptyContainerItem(LsItems.WOOD_RACK.get(), "wood_rack_filled", "wood_rack");

        generateFlatItem(LsItems.LOGINAR_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(LsItems.FRIENDLY_LOGINAR_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
    }

    private void generateLoginarUrn(DeferredBlock<?> block) {
        var urnBlockModel = ModelLocationUtils.getModelLocation(block.get());
        var tintedItemModel = ItemModelUtils.tintedModel(
                urnBlockModel,
                LsItemTintSources.urnClayColor(),
                LsItemTintSources.urnGemColor()
        );
        this.itemModelOutput.accept(block.asItem(), tintedItemModel);
    }

    private void generatedFilledAndEmptyContainerItem(Item item, String filledTexture, String emptyTexture) {
        var emptyKey = ModelLocationUtils.getModelLocation(item);
        var filledKey = ModelLocationUtils.getModelLocation(item, "_filled");
        ModelTemplates.FLAT_ITEM.create(
                emptyKey,
                TextureMapping.layer0(LoginarMod.getId("item/" + emptyTexture)),
                this.modelOutput
        );
        ModelTemplates.FLAT_ITEM.create(
                filledKey,
                TextureMapping.layer0(LoginarMod.getId("item/" + filledTexture)),
                this.modelOutput
        );
        var emptyModel = ItemModelUtils.plainModel(emptyKey);
        var filledModel = ItemModelUtils.plainModel(filledKey);
        generatedFilledAndEmptyContainerItem(item, filledModel, emptyModel);
    }

    private void generatePotionPouch(Item item, String filledTexture, String emptyTexture, String overlayTexture) {
        var filledKey = ModelLocationUtils.getModelLocation(item);
        var emptyKey = ModelLocationUtils.getModelLocation(item, "_empty");
        ModelTemplates.FLAT_ITEM.create(
                emptyKey,
                TextureMapping.layer0(LoginarMod.getId("item/" + emptyTexture)),
                this.modelOutput
        );
        ModelTemplates.TWO_LAYERED_ITEM.create(
                filledKey,
                TextureMapping.layered(
                        LoginarMod.getId("item/" + filledTexture),
                        LoginarMod.getId("item/" + overlayTexture)
                ),
                this.modelOutput
        );
        var emptyModel = ItemModelUtils.plainModel(emptyKey);
        var filledModel = ItemModelUtils.tintedModel(filledKey, ItemModelUtils.constantTint(-1), LsItemTintSources.nextPotionInPouch());
        generatedFilledAndEmptyContainerItem(item, filledModel, emptyModel);
    }

    private void generatedFilledAndEmptyContainerItem(Item item, ItemModel.Unbaked filledModel, ItemModel.Unbaked emptyModel) {
        this.itemModelOutput.accept(
                item,
                ItemModelUtils.conditional(
                        ContainsItems.INSTANCE,
                        filledModel,
                        emptyModel
                )
        );
    }
}
