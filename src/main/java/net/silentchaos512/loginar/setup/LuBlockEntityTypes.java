package net.silentchaos512.loginar.setup;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.lib.block.IBlockProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;

import java.util.Arrays;

public class LuBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LoginarMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> LOGINAR_URN = register("loginar_urn",
            LoginarUrnBlockEntity::new,
            LuBlocks.LOGINAR_URN
    );

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, IBlockProvider... blocks) {
        return REGISTER.register(name, () -> {
            Block[] validBlocks = Arrays.stream(blocks).map(IBlockProvider::asBlock).toArray(Block[]::new);
            //noinspection ConstantConditions - null in build
            return BlockEntityType.Builder.of(factory, validBlocks).build(null);
        });
    }
}
