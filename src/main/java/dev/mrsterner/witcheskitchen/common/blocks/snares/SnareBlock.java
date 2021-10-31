package dev.mrsterner.witcheskitchen.common.blocks.snares;

import dev.mrsterner.witcheskitchen.common.util.SnareDamageSource;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class SnareBlock extends PlantBlock {
    public static final BooleanProperty CLOSED;

    public SnareBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CLOSED, false));
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity user) {
            if (!world.isClient) {
                entity.slowMovement(state, new Vec3d(0.800000011920929D, 0.75D, 0.800000011920929D));
                user.damage(new SnareDamageSource(), 2.0F);
                world.setBlockState(pos, (BlockState)state.with(CLOSED, true), 3);
                world.getBlockTickScheduler().schedule(pos, this, 30);
            }
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(CLOSED)) {
            world.setBlockState(pos, (BlockState)state.with(CLOSED, false), 3);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CLOSED);
        super.appendProperties(builder);
    }

    static {
        CLOSED = BooleanProperty.of("closed");
    }
}
