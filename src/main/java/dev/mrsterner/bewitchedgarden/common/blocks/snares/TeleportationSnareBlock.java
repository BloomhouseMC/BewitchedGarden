package dev.mrsterner.bewitchedgarden.common.blocks.snares;

import dev.mrsterner.bewitchedgarden.common.util.SnareDamageSource;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TeleportationSnareBlock extends SnareBlock {

    public TeleportationSnareBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity user) {
            if (!world.isClient) {
                entity.slowMovement(state, new Vec3d(0.800000011920929D, 0.75D, 0.800000011920929D));
                user.damage(new SnareDamageSource(), 2.0F);
                BWUtil.attemptTeleport(user, user.getBlockPos(), 40, true);
                world.setBlockState(pos, (BlockState)state.with(CLOSED, true), 3);
                world.getBlockTickScheduler().schedule(pos, this, 30);
            }
        }
    }
}
