package dev.mrsterner.witcheskitchen.common.blocks;

import dev.mrsterner.witcheskitchen.common.registry.WKObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Random;

public class CorruptedDirt extends SpreadableBlock {
    public CorruptedDirt(Settings settings) {
        super(settings);
    }

    private static boolean canBeCorrupted(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = worldView.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if(blockState.isOf(Blocks.DIRT)){
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else{
            int i = ChunkLightProvider.getRealisticOpacity(worldView, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(worldView, blockPos));
            return i < worldView.getMaxLightLevel();
        }
    }

    public static boolean canPropagate(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canBeCorrupted(state, worldView, pos) && !worldView.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = this.getDefaultState();
        for (int i = 0; i < 4; ++i) {
            BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            System.out.println(world.getBlockState(blockPos).isOf(Blocks.DIRT)+" : "+canBeCorrupted(blockState, world, blockPos));
            if (world.getBlockState(blockPos).isOf(Blocks.DIRT)){
                world.setBlockState(blockPos, blockState);
            }
            if(world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK) && canPropagate(blockState, world, blockPos)){
                BlockState blockState1 = WKObjects.CORRUPTED_GRASS.getDefaultState();
                world.setBlockState(blockPos, blockState1);
            }
        }
    }
}
