package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.lib.block.IBlockProvider;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.util.Const;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LoginarMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBlocks();

        ModelFile itemGenerated = getExistingFile(new ResourceLocation("item/generated"));

        builder(LsItems.LOGINAR_ANTENNA, itemGenerated)
                .override()
                .predicate(Const.IS_LOGINAR_CHUNK, 1f)
                .model(getBuilder("loginar_antenna_lit").parent(itemGenerated).texture("layer0", "item/loginar_antenna_lit"))
                .end();
        builder(LsItems.LOGINAR_TENTACLE, itemGenerated);
        builder(LsItems.LOGINAR_CALAMARI, itemGenerated);

        builder(LsItems.ITEM_SWAPPER_UPGRADE, itemGenerated);
        builder(LsItems.HOTBAR_SWAPPER_UPGRADE, itemGenerated);

        builder(LsItems.LOGINAR_SPAWN_EGG)
                .parent(getExistingFile(mcLoc("item/template_spawn_egg")));
    }

    private void registerBlocks() {
        ResourceLocation urnBlockModel = modLoc("block/loginar_urn");
        blockItemModel(LsBlocks.TINY_LOGINAR_URN, urnBlockModel);
        blockItemModel(LsBlocks.SMALL_LOGINAR_URN, urnBlockModel);
        blockItemModel(LsBlocks.MEDIUM_LOGINAR_URN, urnBlockModel);
        blockItemModel(LsBlocks.LARGE_LOGINAR_URN, urnBlockModel);
        blockItemModel(LsBlocks.HUGE_LOGINAR_URN, urnBlockModel);
        blockItemModel(LsBlocks.SUPER_LOGINAR_URN, urnBlockModel);
    }

    private ItemModelBuilder builder(ItemLike item) {
        return getBuilder(NameUtils.fromItem(item).getPath());
    }

    private ItemModelBuilder builder(ItemLike item, ModelFile parent) {
        String name = NameUtils.fromItem(item).getPath();
        return builder(item, parent, "item/" + name);
    }

    private ItemModelBuilder builder(ItemLike item, ModelFile parent, String texture) {
        return getBuilder(NameUtils.fromItem(item).getPath()).parent(parent).texture("layer0", texture);
    }

    private void blockItemModel(IBlockProvider block) {
        String name = NameUtils.from(block.asBlock()).getPath();
        blockItemModel(block, modLoc("block/" + name));
    }

    private void blockItemModel(IBlockProvider block, ResourceLocation parent) {
        String name = NameUtils.from(block.asBlock()).getPath();
        withExistingParent(name, parent);
    }
}
