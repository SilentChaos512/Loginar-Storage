package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LuBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LoginarMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(LuBlocks.LOGINAR_URN.get(), models()
                .getExistingFile(modLoc("block/loginar_urn"))
        );
    }
}
