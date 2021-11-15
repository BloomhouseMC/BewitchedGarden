package com.bloomhousemc.bewitchedgarden.common.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MossStaffItem extends BaseStaffItem {

    public MossStaffItem(int maxStorage, Settings settings) {
        super(maxStorage, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (this.getStoredPower() >= 10) {
            if (!world.isClient) {
                world.setBlockState(this.randomTL(world, user), Blocks.MOSS_BLOCK.getDefaultState());
                world.setBlockState(this.randomTR(world, user), Blocks.MOSS_BLOCK.getDefaultState());
                world.setBlockState(this.randomBR(world, user), Blocks.MOSS_BLOCK.getDefaultState());
                world.setBlockState(this.randomTR(world, user), Blocks.AZALEA.getDefaultState());
                world.setBlockState(this.randomTL(world, user), Blocks.AZALEA_LEAVES.getDefaultState());
                world.setBlockState(this.randomBR(world, user), Blocks.FLOWERING_AZALEA.getDefaultState());
                world.setBlockState(this.randomBL(world, user), Blocks.MOSS_BLOCK.getDefaultState());
                this.remove(10);
                this.checkForOverfill();
            }
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.pass(stack);
    }

    public BlockPos randomTL(World world, PlayerEntity player) {
        BlockPos pos = player.getBlockPos();
        BlockPos randomPos = pos.add(MathHelper.nextInt(player.getRandom(), 0, 5), 0, MathHelper.nextInt(player.getRandom(), 0, 5));
        return randomPos;
    }

    public BlockPos randomTR(World world, PlayerEntity player) {
        BlockPos pos = player.getBlockPos();
        BlockPos randomPos = pos.add(MathHelper.nextInt(player.getRandom(), 0, 5), 0, MathHelper.nextInt(player.getRandom(), -5, -1));
        return randomPos;
    }

    public BlockPos randomBR(World world, PlayerEntity player) {
        BlockPos pos = player.getBlockPos();
        BlockPos randomPos = pos.add(MathHelper.nextInt(player.getRandom(), -5, -1), 0, MathHelper.nextInt(player.getRandom(), -5, -1));
        return randomPos;
    }

    public BlockPos randomBL(World world, PlayerEntity player) {
        BlockPos pos = player.getBlockPos();
        BlockPos randomPos = pos.add(MathHelper.nextInt(player.getRandom(), -5, -1), 0, MathHelper.nextInt(player.getRandom(), 0, 5));
        return randomPos;
    }
}
