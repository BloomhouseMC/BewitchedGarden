package dev.mrsterner.bewitchedgarden.common.blocks;

import dev.mrsterner.bewitchedgarden.common.blocks.blockentity.SausageBlockEntity;
import dev.mrsterner.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class SausageBlock extends Block implements BlockEntityProvider {
    public static IntProperty SAUSAGES = IntProperty.of("sausage", 1, 4);
    public SausageBlock(Settings settings) {
        super(settings.nonOpaque().breakInstantly());
        setDefaultState(getStateManager().getDefaultState().with(SAUSAGES, 1));
    }
    private static final VoxelShape SHAPE =
    VoxelShapes.union(
    createCuboidShape(2, 0, 2, 14, 16, 14));

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SAUSAGES);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SausageBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        state.with(SAUSAGES, 1);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        return !blockState.isOf(Blocks.AIR);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if(context.getStack().getItem() == (BGObjects.SAUSAGE_ITEM.asItem())){
            return (Integer) state.get(SAUSAGES) < 4;
        }
        return super.canReplace(state, context);

    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return (BlockState)blockState.with(SAUSAGES, Math.min(4, (Integer)blockState.get(SAUSAGES) + 1));
        }
        return (BlockState)super.getPlacementState(ctx);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        for(int i = state.get(SAUSAGES); i > 0;i--){
            dropStack(world, pos, new ItemStack(BGObjects.SAUSAGE_ITEM));
        }
    }
}
