package com.bloomhousemc.bewitchedgarden.common.blocks;

import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.HeavensGateBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.bloomhousemc.bewitchedgarden.common.util.MultiBlock4x4;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
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
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class HeavensGateBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static BooleanProperty OPEN = BooleanProperty.of("open");

    public static final EnumProperty<MultiBlock4x4> PIECE = EnumProperty.of("piece", MultiBlock4x4.class);
    public HeavensGateBlock(Settings settings) {
        super(settings.nonOpaque());
        setDefaultState(this.stateManager.getDefaultState()
        .with(PIECE, MultiBlock4x4.M2X1)
        .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
        .with(OPEN, false));
    }



    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HORIZONTAL_FACING, OPEN).add(PIECE);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient){
            BlockState block = BGObjects.EMPTY_HEAVENS_GATE.getDefaultState().with(OPEN, false);
            if(world.getBlockEntity(pos) instanceof HeavensGateBlockEntity heavensGateBlockEntity){
                for(int x = 0; x < 3; x++){
                    for(int y = 0; y < 4; y++){
                        if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.EAST) || state.get(Properties.HORIZONTAL_FACING).equals(Direction.WEST)){
                            world.setBlockState(pos.subtract(new Vec3i(0,0,1))
                            .add(0,y,x), block.with(PIECE, MultiBlock4x4.getMulti(x+1,y+1)).with(Properties.HORIZONTAL_FACING ,state.get(Properties.HORIZONTAL_FACING)));

                        }else{
                            world.setBlockState(pos.subtract(new Vec3i(1,0,0))
                            .add(x,y,0), block.with(PIECE, MultiBlock4x4.getMulti(x+1,y+1)).with(Properties.HORIZONTAL_FACING ,state.get(Properties.HORIZONTAL_FACING)));
                        }
                    }
                }
                world.setBlockState(pos, this.getDefaultState().with(PIECE, MultiBlock4x4.M2X1).with(Properties.HORIZONTAL_FACING ,state.get(Properties.HORIZONTAL_FACING)));
            }
        }
    }



    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
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


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HeavensGateBlockEntity(pos,state);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        ArrayList<BlockPos> blockPosArrayList = new ArrayList<>();
        for(int x = 0; x < 2 + 1; x++){
            for(int y = 0; y < 3 + 1; y++){
                if(state.get(Properties.HORIZONTAL_FACING) == Direction.SOUTH || state.get(Properties.HORIZONTAL_FACING) == Direction.NORTH){
                    if(!world.getBlockState(pos.subtract(new Vec3i(1,0,0)).add(x,y,0)).isAir())
                    return false;
                }else{
                    if(!world.getBlockState(pos.subtract(new Vec3i(0,0,1)).add(0,y,x)).isAir())
                    return false;
                }
            }
        }
        return super.canPlaceAt(state, world, pos);
    }


    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        for(int x = 0; x < 2 + 1; x++){
            for(int y = 0; y < 3 + 1; y++){
                if(state.get(Properties.HORIZONTAL_FACING) == Direction.SOUTH || state.get(Properties.HORIZONTAL_FACING) == Direction.NORTH){
                    world.breakBlock(pos
                    .subtract(new Vec3i(1,0,0))
                    .add(x,y,0), false ,player, 3);
                }else{
                    world.breakBlock(pos
                    .subtract(new Vec3i(0,0,1))
                    .add(0,y,x), false ,player, 3);
                }
            }
        }
        super.onBreak(world, pos, state, player);

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        HeavensGateBlockEntity blockEntity = (HeavensGateBlockEntity) world.getBlockEntity(pos);
        if(blockEntity.getCounter() == 0 || blockEntity.getCounter() == blockEntity.MAX_COUNT){
            world.setBlockState(pos, state.with(OPEN, !state.get(OPEN)));
        }

        return ActionResult.SUCCESS;
    }

}
