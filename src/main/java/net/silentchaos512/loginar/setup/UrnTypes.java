package net.silentchaos512.loginar.setup;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.lib.registry.BlockRegistryObject;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;

import java.util.function.Supplier;

public enum UrnTypes {
    TINY(1, () -> LsBlocks.TINY_LOGINAR_URN, () -> LsBlockEntityTypes.TINY_LOGINAR_URN),
    SMALL(2, () -> LsBlocks.SMALL_LOGINAR_URN, () -> LsBlockEntityTypes.SMALL_LOGINAR_URN),
    MEDIUM(3, () -> LsBlocks.MEDIUM_LOGINAR_URN, () -> LsBlockEntityTypes.MEDIUM_LOGINAR_URN),
    LARGE(4, () -> LsBlocks.LARGE_LOGINAR_URN, () -> LsBlockEntityTypes.LARGE_LOGINAR_URN),
    HUGE(5, () -> LsBlocks.HUGE_LOGINAR_URN, () -> LsBlockEntityTypes.HUGE_LOGINAR_URN),
    SUPER(6, () -> LsBlocks.SUPER_LOGINAR_URN, () -> LsBlockEntityTypes.SUPER_LOGINAR_URN);

    private final int inventorySize;
    private final Supplier<BlockRegistryObject<LoginarUrnBlock>> block;
    private final Supplier<RegistryObject<BlockEntityType<LoginarUrnBlockEntity>>> blockEntity;

    UrnTypes(int inventoryRowCount,
             Supplier<BlockRegistryObject<LoginarUrnBlock>> block,
             Supplier<RegistryObject<BlockEntityType<LoginarUrnBlockEntity>>> blockEntity) {
        this.inventorySize = 9 * inventoryRowCount;
        this.block = block;
        this.blockEntity = blockEntity;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public BlockRegistryObject<LoginarUrnBlock> block() {
        return block.get();
    }

    public RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> blockEntity() {
        return blockEntity.get();
    }
}
