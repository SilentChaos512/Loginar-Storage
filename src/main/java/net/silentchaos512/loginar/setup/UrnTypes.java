package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.lib.registry.BlockRegistryObject;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum UrnTypes {
    TINY(1, 1, () -> LsBlocks.TINY_LOGINAR_URN, () -> LsBlockEntityTypes.TINY_LOGINAR_URN),
    SMALL(2, 1, () -> LsBlocks.SMALL_LOGINAR_URN, () -> LsBlockEntityTypes.SMALL_LOGINAR_URN),
    MEDIUM(3, 2, () -> LsBlocks.MEDIUM_LOGINAR_URN, () -> LsBlockEntityTypes.MEDIUM_LOGINAR_URN),
    LARGE(4, 2, () -> LsBlocks.LARGE_LOGINAR_URN, () -> LsBlockEntityTypes.LARGE_LOGINAR_URN),
    HUGE(5, 3, () -> LsBlocks.HUGE_LOGINAR_URN, () -> LsBlockEntityTypes.HUGE_LOGINAR_URN),
    SUPER(6, 3, () -> LsBlocks.SUPER_LOGINAR_URN, () -> LsBlockEntityTypes.SUPER_LOGINAR_URN);

    private final int inventorySize;
    private final int upgradeSlots;
    private final Supplier<BlockRegistryObject<LoginarUrnBlock>> block;
    private final Supplier<RegistryObject<BlockEntityType<LoginarUrnBlockEntity>>> blockEntity;

    UrnTypes(int inventoryRowCount,
             int upgradeSlots, Supplier<BlockRegistryObject<LoginarUrnBlock>> block,
             Supplier<RegistryObject<BlockEntityType<LoginarUrnBlockEntity>>> blockEntity) {
        this.inventorySize = 9 * inventoryRowCount;
        this.upgradeSlots = upgradeSlots;
        this.block = block;
        this.blockEntity = blockEntity;
    }

    public int inventorySize() {
        return inventorySize;
    }

    public int upgradeSlots() {
        return upgradeSlots;
    }

    public BlockRegistryObject<LoginarUrnBlock> block() {
        return block.get();
    }

    public RegistryObject<BlockEntityType<LoginarUrnBlockEntity>> blockEntity() {
        return blockEntity.get();
    }

    @Nullable
    public static UrnTypes fromItem(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem) {
            Block block = ((BlockItem) stack.getItem()).getBlock();
            for (UrnTypes type : values()) {
                if (type.block.get().get().equals(block)) {
                    return type;
                }
            }
        }

        return null;
    }
}
