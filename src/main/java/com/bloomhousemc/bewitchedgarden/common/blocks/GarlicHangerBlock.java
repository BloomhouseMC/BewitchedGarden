package com.bloomhousemc.bewitchedgarden.common.blocks;

import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.GarlicHangerBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.SausageBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
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

public class GarlicHangerBlock extends Block implements BlockEntityProvider {
    public GarlicHangerBlock(Settings settings) {
        super(settings.nonOpaque().breakInstantly());
    }
    private static final VoxelShape SHAPE =
    VoxelShapes.union(
    createCuboidShape(2, 0, 2, 14, 16, 14));

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GarlicHangerBlockEntity(pos, state);
    }


    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        return !blockState.isOf(Blocks.AIR);
    }
}
