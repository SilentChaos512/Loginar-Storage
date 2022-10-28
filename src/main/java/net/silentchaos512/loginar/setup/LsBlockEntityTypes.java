package net.silentchaos512.loginar.setup;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.lib.block.IBlockProvider;
import net.silentchaos512.lib.registry.BlockRegistryObject;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;

import java.util.Arrays;

public class LsBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LoginarMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> TINY_LOGINAR_URN = registerUrn(UrnTypes.TINY, 1);
    public static final RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> SMALL_LOGINAR_URN = registerUrn(UrnTypes.SMALL, 2);
    public static final RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> MEDIUM_LOGINAR_URN = registerUrn(UrnTypes.MEDIUM, 3);
    public static final RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> LARGE_LOGINAR_URN = registerUrn(UrnTypes.LARGE, 4);
    public static final RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> HUGE_LOGINAR_URN = registerUrn(UrnTypes.HUGE, 5);
    public static final RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> SUPER_LOGINAR_URN = registerUrn(UrnTypes.SUPER, 6);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, IBlockProvider... blocks) {
        return REGISTER.register(name, () -> {
            Block[] validBlocks = Arrays.stream(blocks).map(IBlockProvider::asBlock).toArray(Block[]::new);
            //noinspection ConstantConditions - null in build
            return BlockEntityType.Builder.of(factory, validBlocks).build(null);
        });
    }

    private static RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> registerUrn(UrnTypes type, int inventoryRowCount) {
        BlockRegistryObject<LoginarUrnBlock> block = type.block();
        return register(
                block.getId().getPath(),
                (pos, state) -> new LoginarUrnBlockEntity(type, pos, state),
                block
        );
    }
}
