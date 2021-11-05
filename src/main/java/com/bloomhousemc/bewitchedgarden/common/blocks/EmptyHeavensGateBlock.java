package com.bloomhousemc.bewitchedgarden.common.blocks;

import com.bloomhousemc.bewitchedgarden.common.util.MultiBlock4x4;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static com.bloomhousemc.bewitchedgarden.common.blocks.HeavensGateBlock.OPEN;

public class EmptyHeavensGateBlock extends HorizontalFacingBlock {
    public static final EnumProperty<MultiBlock4x4> PIECE = EnumProperty.of("piece", MultiBlock4x4.class);
    public EmptyHeavensGateBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState()
        .with(PIECE, MultiBlock4x4.M3X2)
        .with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HORIZONTAL_FACING, OPEN).add(PIECE);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        MultiBlock4x4.Point point = MultiBlock4x4.getPoint(state.get(PIECE).asString());
        switch (state.get(Properties.HORIZONTAL_FACING)) {
            case NORTH, SOUTH -> {
                BlockPos newPos = pos.subtract(new Vec3i(point.x - 2,point.y - 1,0));
                world.setBlockState(newPos, world.getBlockState(newPos).with(OPEN, !world.getBlockState(newPos).get(OPEN)));
            }
            case EAST, WEST -> {
                BlockPos newPos = pos.subtract(new Vec3i(0,point.y - 1,point.x - 2));
                world.setBlockState(newPos, world.getBlockState(newPos).with(OPEN, !world.getBlockState(newPos).get(OPEN)));
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case NORTH -> VoxelShapes.cuboid(0.0f, 0.0f, 0.8f, 1.0f, 1.0f, 1.0f);
            case SOUTH -> VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.2f);
            case EAST -> VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 0.2f, 1.0f, 1.0f);
            case WEST -> VoxelShapes.cuboid(0.8f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            default -> VoxelShapes.fullCube();
        };
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        MultiBlock4x4 mutliBlock = state.get(PIECE);
        MultiBlock4x4.Point point = MultiBlock4x4.getPoint(mutliBlock.asString());
        for(int x = 0; x < 2 + 1; x++){
            for(int y = 0; y < 3 + 1; y++){
                if(state.get(Properties.HORIZONTAL_FACING) == Direction.SOUTH || state.get(Properties.HORIZONTAL_FACING) == Direction.NORTH){
                    world.breakBlock(pos
                    .subtract(new Vec3i(point.x-1,point.y-1,0))
                    .add(x,y,0), false ,player, 3);
                }else{
                    world.breakBlock(pos
                    .subtract(new Vec3i(0,point.y-1,point.x-1))
                    .add(0,y,x), false ,player, 3);
                }
            }
        }
    }
}
