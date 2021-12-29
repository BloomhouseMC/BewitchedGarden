package com.bloomhousemc.bewitchedgarden.common.fluids;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.bloomhousemc.bewitchedgarden.common.registry.BGTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public abstract class PoisonFluid extends FlowableFluid {

    @Override
    public Fluid getFlowing() {
        return BGObjects.POISON_FLUID_FLOWING;
    }

    @Override
    public Fluid getStill() {
        return BGObjects.POISON_FLUID_STILL;
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 2;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    @Override
    public Item getBucketItem() {
        return BGObjects.POISON_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 30;
    }

    @Override
    protected float getBlastResistance() {
        return 100F;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return BGObjects.POISON.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }




    public static void breathing(LivingEntity thisEntity) {
        boolean invulnerable = thisEntity instanceof PlayerEntity playerEntity && playerEntity.getAbilities().invulnerable;
        if (thisEntity.isAlive()) {
            if (thisEntity.isSubmergedIn(BGTags.POISON)) {
                if (!thisEntity.canBreatheInWater() && !invulnerable) {
                    thisEntity.setAir(decreaseAirSupply(thisEntity.getAir() - 4, thisEntity, thisEntity.world.random));
                    if (thisEntity.getAir() == -20) {
                        thisEntity.setAir(0);
                        Vec3f vector3d = thisEntity.getMovementDirection().getUnitVector();

                        for(int i = 0; i < 8; ++i) {
                            double d2 = thisEntity.world.random.nextDouble() - thisEntity.world.random.nextDouble();
                            double d3 = thisEntity.world.random.nextDouble() - thisEntity.world.random.nextDouble();
                            double d4 = thisEntity.world.random.nextDouble() - thisEntity.world.random.nextDouble();
                            thisEntity.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, thisEntity.getX() + d2, thisEntity.getY() + d3, thisEntity.getZ() + d4, vector3d.getX(), vector3d.getY(), vector3d.getZ());
                        }

                        thisEntity.damage(DamageSource.DROWN, 2.0F);
                    }
                }
            }
        }
    }
    protected static int decreaseAirSupply(int airSupply, LivingEntity entity, Random random) {
        int respiration = EnchantmentHelper.getRespiration(entity);
        return respiration > 0 && random.nextInt(respiration + 1) > 0 ? airSupply : airSupply - 1;
    }

    public static class Flowing extends PoisonFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends PoisonFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
