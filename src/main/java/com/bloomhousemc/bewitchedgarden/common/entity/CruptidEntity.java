package com.bloomhousemc.bewitchedgarden.common.entity;

import com.bloomhousemc.bewitchedgarden.common.blocks.CorruptedDirt;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.Random;

public class CruptidEntity extends HostileEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);

    public CruptidEntity(EntityType<? extends CruptidEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(3, new CruptidEntity.WanderAndCorruptGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.5D));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createCruptidAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos.down()).isOf(BGObjects.CORRUPTED_DIRT) || world.getBlockState(pos.down()).isOf(BGObjects.CORRUPTED_GRASS) ? 10.0F : super.getPathfindingFavor(pos, world);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SILVERFISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SILVERFISH_DEATH;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.13F;
    }

    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    private <E extends IAnimatable> PlayState basicMovement(AnimationEvent<E> event) {
        if (event.getLimbSwingAmount()>0.1F){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cruptid.move", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cruptid.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "BasicMovement", 0, this::basicMovement));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    static class WanderAndCorruptGoal extends WanderAroundGoal {
        private Direction direction;
        private boolean canInfest;

        public WanderAndCorruptGoal(CruptidEntity cruptid) {
            super(cruptid, 1.0D, 10);
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            if (this.mob.getTarget() != null) {
                return false;
            } else if (!this.mob.getNavigation().isIdle()) {
                return false;
            } else {
                Random random = this.mob.getRandom();
                if (this.mob.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && random.nextInt(10) == 0) {
                    this.direction = Direction.random(random);
                    BlockPos blockPos = (new BlockPos(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ())).offset(this.direction);
                    this.canInfest = true;
                    return true;
                }

                this.canInfest = false;
                return super.canStart();
            }
        }

        public boolean shouldContinue() {
            return this.canInfest ? false : super.shouldContinue();
        }

        public void start() {
            if (!this.canInfest) {
                super.start();
            } else {
                WorldAccess worldAccess = this.mob.world;
                BlockPos blockPos = (new BlockPos(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ())).offset(this.direction);
                BlockState blockState = worldAccess.getBlockState(blockPos);
                if (blockState.isOf(Blocks.GRASS_BLOCK) && CorruptedDirt.canPropagate(blockState, this.mob.world, blockPos)) {
                    worldAccess.setBlockState(blockPos, BGObjects.CORRUPTED_GRASS.getDefaultState(), 3);
                } else if (blockState.isOf(Blocks.DIRT)) {
                    worldAccess.setBlockState(blockPos, BGObjects.CORRUPTED_DIRT.getDefaultState(), 3);
                }
            }
        }
    }
}
