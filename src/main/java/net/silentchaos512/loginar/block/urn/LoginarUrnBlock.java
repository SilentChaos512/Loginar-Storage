package net.silentchaos512.loginar.block.urn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.silentchaos512.utils.Color;
import org.jetbrains.annotations.Nullable;

public class LoginarUrnBlock extends BaseEntityBlock {
    public LoginarUrnBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LoginarUrnBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static int getBlockColor(BlockState state, @Nullable BlockGetter level, @Nullable BlockPos pos, int tintIndex) {
        if (level != null && pos != null) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof LoginarUrnBlockEntity) {
                LoginarUrnBlockEntity urn = (LoginarUrnBlockEntity) blockEntity;

                if (tintIndex == 0) {
                    // Main body (clay)
                    return urn.getClayColor();
                } else if (tintIndex == 1) {
                    // Decorative gem
                    return urn.getGemColor();
                }
            }
        }
        return Color.VALUE_WHITE;
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        // TODO
        if (tintIndex == 0) {
            // Main body (clay)
            return 0x985F45;
        } else if (tintIndex == 1) {
            return 0x33EBCB;
        }
        return Color.VALUE_WHITE;
    }
}
