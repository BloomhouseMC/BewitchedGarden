package com.bloomhousemc.bewitchedgarden.common.blocks;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class PoisonBlock extends FluidBlock {
    public PoisonBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    public static class PoisonPuddle extends Block{
        protected final FlowableFluid fluid;
        public static final VoxelShape COLLISION_SHAPE = createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 0.5D, 16.0D);;

        public PoisonPuddle(FlowableFluid fluid, AbstractBlock.Settings settings) {
            super(settings.breakInstantly());
            this.fluid = fluid;
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if(player.getStackInHand(hand).getItem() instanceof GlassBottleItem){
                BWUtil.addItemToInventoryAndConsume(player, hand, new ItemStack(BGObjects.MUNCHER_POISON));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1,1);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }

        @Override
        public boolean hasRandomTicks(BlockState state) {
            return true;
        }
        @Override
        public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            if (!world.isRaining() && random.nextInt(1000) == 0) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            this.scheduledTick(state, world, pos, random);
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return VoxelShapes.empty();
        }
        @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return  COLLISION_SHAPE;
        }
        @Override
        public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
            return VoxelShapes.empty();
        }

        @Environment(EnvType.CLIENT)
        public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
            return stateFrom.getFluidState().getFluid().matchesType(this.fluid);
        }

        @Override
        public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
            if (world.getBlockState(pos) == Blocks.AIR.getDefaultState() || world.getBlockState(pos) == BGObjects.POISON_PUDDLE.getDefaultState()) {
                int i;
                for (i = 2; i < 6; ++i) {
                    BlockPos blockPos = pos.up();
                    BlockPos blockPos1 = blockPos.offset(Direction.byId(i));
                    if (!world.getFluidState(blockPos.offset(Direction.byId(i))).isEmpty()) {
                        return false;
                    }
                    if (!world.getFluidState(blockPos1.offset(Direction.byId(i).rotateYClockwise())).isEmpty()) {
                        return false;
                    }
                }
                for (i = 2; i < 6; ++i) {
                    BlockPos pos1 = pos.down();
                    BlockPos pos2 = pos1.offset(Direction.byId(i));
                    if (!world.getFluidState(pos1.offset(Direction.byId(i))).isEmpty()) {
                        return false;
                    }
                    if (!world.getFluidState(pos2.offset(Direction.byId(i).rotateYClockwise())).isEmpty()) {
                        return false;
                    }
                }
                return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos, Direction.UP);
            }
            else return false;
        }
        public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
            return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
        }
    }
}
