package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.util.Const;

import javax.annotation.Nullable;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), LoginarMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBlocks();

        ModelFile itemGenerated = getExistingFile(ResourceLocation.withDefaultNamespace("item/generated"));

        builder(LsItems.LOGINAR_ANTENNA, itemGenerated)
                .override()
                .predicate(Const.IS_LOGINAR_CHUNK, 1f)
                .model(getBuilder("loginar_antenna_lit").parent(itemGenerated).texture("layer0", "item/loginar_antenna_lit"))
                .end();
        builder(LsItems.LOGINAR_TENTACLE, itemGenerated);
        builder(LsItems.LOGINAR_CALAMARI, itemGenerated);
        builder(LsItems.FIRE_PEARL, itemGenerated);
        builder(LsItems.FIRE_FLINGER, itemGenerated);

        builder(LsItems.BACKPACK_UPGRADE, itemGenerated);
        builder(LsItems.VACUUM_UPGRADE, itemGenerated);
        builder(LsItems.ITEM_SWAPPER_UPGRADE, itemGenerated);
        builder(LsItems.SUPPLIER_UPGRADE, itemGenerated);

        builder(LsItems.LUNCH_BOX, itemGenerated);
        itemWithEmptyModel(LsItems.POTION_POUCH, itemGenerated, "potion_pouch", "potion_pouch_empty", "potion_pouch_overlay");
        builder(LsItems.GEM_BAG, itemGenerated);
        builder(LsItems.FLOWER_BASKET, itemGenerated);
        builder(LsItems.ORE_CRATE, itemGenerated);

        builder(LsItems.LOGINAR_SPAWN_EGG)
                .parent(getExistingFile(mcLoc("item/template_spawn_egg")));
    }

    private void registerBlocks() {
        blockItemModel(LsBlocks.TINY_LOGINAR_URN);
        blockItemModel(LsBlocks.SMALL_LOGINAR_URN);
        blockItemModel(LsBlocks.MEDIUM_LOGINAR_URN);
        blockItemModel(LsBlocks.LARGE_LOGINAR_URN);
        blockItemModel(LsBlocks.HUGE_LOGINAR_URN);
        blockItemModel(LsBlocks.SUPER_LOGINAR_URN);
    }

    private ItemModelBuilder builder(DeferredItem<?> item) {
        return getBuilder(item.getId().getPath());
    }

    private ItemModelBuilder builder(DeferredItem<?> item, ModelFile parent) {
        String name = item.getId().getPath();
        return builder(item, parent, "item/" + name);
    }

    private ItemModelBuilder builder(DeferredItem<?> item, ModelFile parent, String texture) {
        return getBuilder(item.getId().getPath()).parent(parent).texture("layer0", texture);
    }

    private void itemWithEmptyModel(DeferredItem<?> item, ModelFile parent, String filledTexture, String emptyTexture, @Nullable String overlayTexture) {
        var filledModelBuilder = getBuilder(item.getId().getPath() + "_filled")
                .parent(parent)
                .texture("layer0", "item/" + filledTexture);
        if (overlayTexture != null) {
            filledModelBuilder.texture("layer1", "item/" + overlayTexture);
        }

        var baseModelBuilder = getBuilder(item.getId().getPath())
                .parent(parent)
                .texture("layer0", "item/" + emptyTexture)
                .override()
                .predicate(Const.FILLED, 1)
                .model(filledModelBuilder)
                .end();
    }

    private void blockItemModel(DeferredBlock<?> block) {
        String name = block.getId().getPath();
        blockItemModel(block, modLoc("block/" + name));
    }

    private void blockItemModel(DeferredBlock<?> block, ResourceLocation parent) {
        String name = block.getId().getPath();
        withExistingParent(name, parent);
    }
}
