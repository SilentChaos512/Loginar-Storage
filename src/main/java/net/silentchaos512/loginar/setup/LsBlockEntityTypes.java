package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;

import java.util.Arrays;

public class LsBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, LoginarMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>> TINY_LOGINAR_URN = registerUrn(UrnTypes.TINY);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>> SMALL_LOGINAR_URN = registerUrn(UrnTypes.SMALL);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>> MEDIUM_LOGINAR_URN = registerUrn(UrnTypes.MEDIUM);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>> LARGE_LOGINAR_URN = registerUrn(UrnTypes.LARGE);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>> HUGE_LOGINAR_URN = registerUrn(UrnTypes.HUGE);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>> SUPER_LOGINAR_URN = registerUrn(UrnTypes.SUPER);

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<?>... blocks) {
        return REGISTER.register(name, () -> {
            Block[] validBlocks = Arrays.stream(blocks).map(DeferredBlock::get).toArray(Block[]::new);
            //noinspection ConstantConditions - null in build
            return new BlockEntityType<>(factory, validBlocks);
        });
    }

    private static DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>> registerUrn(UrnTypes type) {
        DeferredBlock<LoginarUrnBlock> block = type.block();
        return register(
                block.getId().getPath(),
                (pos, state) -> new LoginarUrnBlockEntity(type, pos, state),
                block
        );
    }
}
