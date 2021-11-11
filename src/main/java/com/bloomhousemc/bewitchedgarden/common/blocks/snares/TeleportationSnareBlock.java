package com.bloomhousemc.bewitchedgarden.common.blocks.snares;

import com.bloomhousemc.bewitchedgarden.common.util.SnareDamageSource;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TeleportationSnareBlock extends SnareBlock {

    public TeleportationSnareBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity user) {
            if (!world.isClient) {
                user.damage(new SnareDamageSource(), 1.0F);
                BWUtil.attemptTeleport(user, user.getBlockPos(), 40, true);
                world.setBlockState(pos, (BlockState)state.with(CLOSED, true), 3);
                world.getBlockTickScheduler().schedule(pos, this, 30);
            }
        }
    }
}
