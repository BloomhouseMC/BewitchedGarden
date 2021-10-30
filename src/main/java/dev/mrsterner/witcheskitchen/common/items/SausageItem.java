package dev.mrsterner.witcheskitchen.common.items;

import dev.mrsterner.witcheskitchen.common.blocks.SausageBlock;
import dev.mrsterner.witcheskitchen.common.blocks.blockentity.SausageBlockEntity;
import dev.mrsterner.witcheskitchen.common.registry.WKObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SausageItem extends Item {
    public SausageItem(Settings settings) {
        super(settings);
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.isSneaking()){
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        BlockPos air = pos.add(0,1,0).add(context.getSide().getVector());
        if(context.getWorld().getBlockState(air).getBlock() == Blocks.AIR && !(context.getWorld().getBlockState(pos).getBlock() instanceof SausageBlock)){
            return ActionResult.PASS;
        }
        if(context.getWorld().getBlockState(pos).getBlock() instanceof SausageBlock){
            SausageBlockEntity blockEntity = (SausageBlockEntity) context.getWorld().getBlockEntity(context.getBlockPos());
            Integer sausages = context.getWorld().getBlockState(pos).get(SausageBlock.SAUSAGES);
            if(sausages < 4){
                context.getWorld().setBlockState(context.getBlockPos(), blockEntity.getCachedState().with(SausageBlock.SAUSAGES, sausages + 1));
                context.getPlayer().getStackInHand(context.getHand()).split(1);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }else{
            context.getWorld().setBlockState(context.getBlockPos().add(context.getSide().getVector()), WKObjects.SAUSAGE.getDefaultState());
        }
        return ActionResult.PASS;
    }
}
