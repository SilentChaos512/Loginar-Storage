package net.silentchaos512.loginar.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.silentchaos512.loginar.entity.FriendlyLoginar;
import net.silentchaos512.loginar.setup.LsEntityTypes;

public class LoginarEggBlock extends Block {
    public static final MapCodec<LoginarEggBlock> CODEC = simpleCodec(LoginarEggBlock::new);
    public static final int MAX_HATCH = 5;
    public static final IntegerProperty HATCH = IntegerProperty.create("loginar_hatch", 0, MAX_HATCH);
    private static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);

    @Override
    protected MapCodec<? extends Block> codec() {
        return super.codec();
    }

    public LoginarEggBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HATCH, 0));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (shouldUpdateHatchLevel(level) && onMagma(level, pos)) {
            int hatch = state.getValue(HATCH);
            if (hatch < MAX_HATCH) {
                level.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                level.setBlock(pos, state.setValue(HATCH, hatch + 1), 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
            } else {
                level.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                level.removeBlock(pos, false);
                level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));

                level.levelEvent(2001, pos, Block.getId(state));
                FriendlyLoginar babyLoginar = LsEntityTypes.FRIENDLY_LOGINAR.get().create(level, EntitySpawnReason.BREEDING);
                if (babyLoginar != null) {
                    babyLoginar.setBaby(true);
                    babyLoginar.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0f);
                    level.addFreshEntity(babyLoginar);
                }
            }
        }
    }

    private boolean shouldUpdateHatchLevel(Level level) {
        float time = level.getTimeOfDay(1.0f);
        if (time < 0.19f && time > 0.15f) return true;
        return level.random.nextInt(50) == 0;
    }

    public boolean onMagma(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK);
    }

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (onMagma(pLevel, pPos) && !pLevel.isClientSide) {
            pLevel.levelEvent(2012, pPos, 15);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HATCH);
    }
}
