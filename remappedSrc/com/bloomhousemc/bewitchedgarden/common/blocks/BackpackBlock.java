package com.bloomhousemc.bewitchedgarden.common.blocks;

import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.BackpackBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BackpackBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public BackpackBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BackpackBlockEntity(pos, state);
    }

    private static final VoxelShape SHAPE = VoxelShapes.union(createCuboidShape(4, 0, 4, 12, 12, 12));

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()) {
            if(player.isSneaking() && player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                if(world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity) {
                    ItemStack stack = new ItemStack(this);
                    NbtCompound tag = stack.getOrCreateNbt();

                    Inventories.writeNbt(tag, blockEntity.inventory);
                    blockEntity.wasPickedUp = true;
                    player.equipStack(EquipmentSlot.CHEST, stack);
                    world.breakBlock(pos, false, player);
                }
            }
            else {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

                if(screenHandlerFactory != null) {
                    world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.BLOCKS, 1F, 1F);
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        }

        return ActionResult.SUCCESS;
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity && !blockEntity.wasPickedUp) {
            if(!world.isClient() && state.getBlock() != newState.getBlock()) {
                ItemScatterer.spawn(world, pos, blockEntity);
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
