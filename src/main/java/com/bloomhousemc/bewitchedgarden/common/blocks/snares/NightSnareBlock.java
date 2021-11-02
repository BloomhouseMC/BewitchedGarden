package com.bloomhousemc.bewitchedgarden.common.blocks.snares;

import com.bloomhousemc.bewitchedgarden.common.util.SnareDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NightSnareBlock extends SnareBlock {

    public NightSnareBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity user) {
            if (!world.isClient) {
                entity.slowMovement(state, new Vec3d(0.800000011920929D, 0.75D, 0.800000011920929D));
                user.damage(new SnareDamageSource(), 1.0F);
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0, true, false));
                world.setBlockState(pos, (BlockState)state.with(CLOSED, true), 3);
                world.getBlockTickScheduler().schedule(pos, this, 30);
            }
        }
    }
}
