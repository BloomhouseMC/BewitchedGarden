package com.bloomhousemc.bewitchedgarden.common.items.staff;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Iterator;

public class PurityStaffItem extends BaseStaffItem {

    public PurityStaffItem(int maxStorage, Settings settings) {
        super(maxStorage, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (this.getStoredPower() >= 30) {
            if (!world.isClient) {
                // Not the most efficient way but it works
                Iterator var = BlockPos.Mutable.iterateOutwards(user.getBlockPos(), 10, 10, 10).iterator();
                while (var.hasNext()) {
                    BlockPos pos = (BlockPos)var.next();
                    if (world.getBlockState(pos).isOf(BGObjects.CORRUPTED_GRASS) || world.getBlockState(pos).isOf(BGObjects.CORRUPTED_DIRT)) {
                        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                        ParticleUtil.spawnParticle(Direction.Axis.Y, world, pos, 0.125D, ParticleTypes.ELECTRIC_SPARK, UniformIntProvider.create(1, 2));
                    }
                }
                this.remove(30);
                this.checkForOverfill();
            }
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.pass(stack);
    }
}
