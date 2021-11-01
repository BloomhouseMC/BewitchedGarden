package dev.mrsterner.bewitchedgarden.common.blocks;

import dev.mrsterner.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Random;

public class CorruptedGrass extends SpreadableBlock {
    public CorruptedGrass(Settings settings) {
        super(settings);
    }

    private static boolean canBeCorrupted(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = worldView.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(worldView, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(worldView, blockPos));
            return i < worldView.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canBeCorrupted(state, worldView, pos) && !worldView.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canPlaceAt(state, world, pos)) {
            world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState());
        } else {
            BlockState blockState = this.getDefaultState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (world.getLightLevel(pos.up()) >= 9 && world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK) && canPropagate(blockState, world, blockPos)){
                    world.setBlockState(blockPos, blockState.with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                }
                if (world.getBlockState(blockPos).isOf(Blocks.DIRT) && CorruptedDirt.canPropagate(blockState, world, blockPos)){
                    BlockState blockState1 = BGObjects.CORRUPTED_DIRT.getDefaultState();
                    world.setBlockState(blockPos, blockState1);
                }
            }
        }
    }
}
