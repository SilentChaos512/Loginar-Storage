package net.silentchaos512.loginar.setup;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockItem;
import net.silentchaos512.loginar.util.UrnRenderInfo;
import net.silentchaos512.loginar.util.UrnSize;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum UrnTypes implements StringRepresentable {
    TINY("tiny",
            new UrnSize(9, 1, 1, 1),
            UrnRenderInfo.STANDARD,
            () -> LsBlocks.TINY_LOGINAR_URN,
            () -> LsBlockEntityTypes.TINY_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(7, 0, 7, 9, 13, 9),
                    Block.box(5.5, 1, 5.5, 10.5, 10, 10.5)
            )
    ),
    SMALL("small",
            new UrnSize(9, 2, 1, 1),
            UrnRenderInfo.STANDARD,
            () -> LsBlocks.SMALL_LOGINAR_URN,
            () -> LsBlockEntityTypes.SMALL_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(7, 0, 7, 9, 13, 9),
                    Block.box(5, 1, 5, 11, 11, 11)
            )
    ),
    MEDIUM("medium",
            new UrnSize(9, 4, 1, 2),
            UrnRenderInfo.STANDARD,
            () -> LsBlocks.MEDIUM_LOGINAR_URN,
            () -> LsBlockEntityTypes.MEDIUM_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(5.5, 0, 5.5, 10.5, 13, 10.5),
                    Block.box(4.5, 1, 4.5, 11.5, 12.5, 11.5)
            )
    ),
    LARGE("large",
            new UrnSize(9, 6, 1, 2),
            UrnRenderInfo.STANDARD,
            () -> LsBlocks.LARGE_LOGINAR_URN,
            () -> LsBlockEntityTypes.LARGE_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(5, 0, 5, 11, 13, 11),
                    Block.box(4, 1, 4, 12, 12, 12)
            )
    ),
    HUGE("huge",
            new UrnSize(9, 9, 1, 3),
            UrnRenderInfo.HUGE_9X9,
            () -> LsBlocks.HUGE_LOGINAR_URN,
            () -> LsBlockEntityTypes.HUGE_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(5, 0, 5, 11, 13, 11),
                    Block.box(3.5, 1, 3.5, 12.5, 12, 12.5)
            )
    ),
    SUPER("super",
            new UrnSize(12, 9, 1, 3),
            UrnRenderInfo.SUPER_9X12,
            () -> LsBlocks.SUPER_LOGINAR_URN,
            () -> LsBlockEntityTypes.SUPER_LOGINAR_URN,
            Shapes.or(
                    Block.box(5.5, 14, 5.5, 10.5, 15, 10.5),
                    Block.box(6.5, 13, 6.5, 9.5, 14, 9.5),
                    Block.box(5, 0, 5, 11, 13, 11),
                    Block.box(2, 1, 2, 14, 12, 14)
            )
    );

    public static final Codec<UrnTypes> CODEC = StringRepresentable.fromEnum(UrnTypes::values);
    public static final StreamCodec<ByteBuf, UrnTypes> STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(
            UrnTypes::byName,
            UrnTypes::getSerializedName
    );

    private final String name;
    private final UrnSize size;
    private final UrnRenderInfo renderInfo;
    private final Supplier<DeferredBlock<LoginarUrnBlock>> block;
    private final Supplier<DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>>> blockEntity;
    private final VoxelShape blockShape;

    UrnTypes(
            String name,
            UrnSize size, UrnRenderInfo renderInfo,
            Supplier<DeferredBlock<LoginarUrnBlock>> block,
            Supplier<DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>>> blockEntity,
            VoxelShape blockShape
    ) {
        this.name = name;
        this.size = size;
        this.renderInfo = renderInfo;
        this.block = block;
        this.blockEntity = blockEntity;
        this.blockShape = blockShape;
    }

    @Nullable
    public static UrnTypes byName(String str) {
        for (UrnTypes type : UrnTypes.values()) {
            if (type.name.equalsIgnoreCase(str)) {
                return type;
            }
        }
        return null;
    }

    public UrnSize size() {
        return this.size;
    }

    public UrnRenderInfo renderInfo() {
        return this.renderInfo;
    }

    public int totalInventorySize() {
        return this.size.getInventorySize();
    }

    public int upgradeSlots() {
        return this.size.upgradeSlots();
    }

    public DeferredBlock<LoginarUrnBlock> block() {
        return block.get();
    }

    public VoxelShape blockShape() {
        return blockShape;
    }

    public DeferredHolder<BlockEntityType<?>, BlockEntityType<LoginarUrnBlockEntity>> blockEntity() {
        return blockEntity.get();
    }

    @Nullable
    public static UrnTypes fromItem(ItemStack stack) {
        if (stack.getItem() instanceof LoginarUrnBlockItem loginarUrnBlockItem) {
            return loginarUrnBlockItem.getUrnType();
        }
        return null;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static UrnTypes read(ByteBuf buf) {
        byte b = buf.readByte();
        return values()[Mth.clamp(b, 0, values().length - 1)];
    }
}
