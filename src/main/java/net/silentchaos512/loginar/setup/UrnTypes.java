package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.lib.registry.BlockRegistryObject;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum UrnTypes {
    TINY(1, 1,
            () -> LsBlocks.TINY_LOGINAR_URN,
            () -> LsBlockEntityTypes.TINY_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(7, 0, 7, 9, 13, 9),
                    Block.box(5.5, 1, 5.5, 10.5, 10, 10.5)
            )
    ),
    SMALL(2, 1,
            () -> LsBlocks.SMALL_LOGINAR_URN,
            () -> LsBlockEntityTypes.SMALL_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(7, 0, 7, 9, 13, 9),
                    Block.box(5, 1, 5, 11, 11, 11)
            )
    ),
    MEDIUM(3, 2,
            () -> LsBlocks.MEDIUM_LOGINAR_URN,
            () -> LsBlockEntityTypes.MEDIUM_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(5.5, 0, 5.5, 10.5, 13, 10.5),
                    Block.box(4.5, 1, 4.5, 11.5, 12.5, 11.5)
            )
    ),
    LARGE(4, 2,
            () -> LsBlocks.LARGE_LOGINAR_URN,
            () -> LsBlockEntityTypes.LARGE_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(5, 0, 5, 11, 13, 11),
                    Block.box(4, 1, 4, 12, 12, 12)
            )
    ),
    HUGE(5, 3,
            () -> LsBlocks.HUGE_LOGINAR_URN,
            () -> LsBlockEntityTypes.HUGE_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(5, 0, 5, 11, 13, 11),
                    Block.box(3.5, 1, 3.5, 12.5, 12, 12.5)
            )
    ),
    SUPER(6, 3,
            () -> LsBlocks.SUPER_LOGINAR_URN,
            () -> LsBlockEntityTypes.SUPER_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(5, 0, 5, 11, 13, 11),
                    Block.box(2, 1, 2, 14, 12, 14)
            )
    );

    private final int inventorySize;
    private final int upgradeSlots;
    private final Supplier<BlockRegistryObject<LoginarUrnBlock>> block;
    private final Supplier<RegistryObject<BlockEntityType<LoginarUrnBlockEntity>>> blockEntity;
    private final VoxelShape blockShape;

    UrnTypes(int inventoryRowCount,
             int upgradeSlots, Supplier<BlockRegistryObject<LoginarUrnBlock>> block,
             Supplier<RegistryObject<BlockEntityType<LoginarUrnBlockEntity>>> blockEntity,
             VoxelShape blockShape) {
        this.inventorySize = 9 * inventoryRowCount;
        this.upgradeSlots = upgradeSlots;
        this.block = block;
        this.blockEntity = blockEntity;
        this.blockShape = blockShape;
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

    public VoxelShape blockShape() {
        return blockShape;
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
