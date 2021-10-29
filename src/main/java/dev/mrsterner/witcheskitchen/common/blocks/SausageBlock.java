package dev.mrsterner.witcheskitchen.common.blocks;

import dev.mrsterner.witcheskitchen.WitchesKitchen;
import dev.mrsterner.witcheskitchen.common.WKUtils;
import dev.mrsterner.witcheskitchen.common.blocks.blockentity.SausageBlockEntity;
import dev.mrsterner.witcheskitchen.common.registry.WKObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SausageBlock extends Block implements BlockEntityProvider {
    public static IntProperty SAUSAGES = IntProperty.of("sausage", 1, 4);
    public SausageBlock(Settings settings) {
        super(settings.nonOpaque().breakInstantly());
        setDefaultState(getStateManager().getDefaultState().with(SAUSAGES, 1));
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
        //placer.getStackInHand(placer.getActiveHand()).split(1);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(state.get(SAUSAGES) < 4 && player.getStackInHand(hand).getItem() == WKObjects.SAUSAGE.asItem()){
            //state.with(SAUSAGES, state.get(SAUSAGES)+1);
            world.setBlockState(pos, state.with(SAUSAGES, state.get(SAUSAGES)+1));
            player.getStackInHand(hand).split(1);
            System.out.println("if"+state.get(SAUSAGES));
            return ActionResult.SUCCESS;
        }else if(state.get(SAUSAGES) < 4 && state.get(SAUSAGES) > 1 && player.getStackInHand(hand).isEmpty()){
            world.setBlockState(pos, state.with(SAUSAGES, state.get(SAUSAGES) - 1));
            WKUtils.addItemToInventoryAndConsume(player,hand,new ItemStack(WKObjects.SAUSAGE));
            System.out.println("elseif"+state.get(SAUSAGES));
        }else{
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            WKUtils.addItemToInventoryAndConsume(player,hand,new ItemStack(WKObjects.SAUSAGE));
            System.out.println("else"+state.get(SAUSAGES));
        }
        return ActionResult.PASS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        for(int i = state.get(SAUSAGES); i > 0;i--){
            dropStack(world, pos, new ItemStack(WKObjects.SAUSAGE));
        }
    }
}
