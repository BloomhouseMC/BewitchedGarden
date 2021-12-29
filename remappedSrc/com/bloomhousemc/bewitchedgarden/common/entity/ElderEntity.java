package com.bloomhousemc.bewitchedgarden.common.entity;

import moriyashiine.bewitchment.common.entity.living.RavenEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Iterator;

public class ElderEntity extends HostileEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);
    private int attackTick;
    private int stunTick;

    public ElderEntity(EntityType<? extends ElderEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
        this.experiencePoints = 20;
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.4D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, RavenEntity.class, 8.0F));
        this.targetSelector.add(2, (new RevengeGoal(this)));
        this.targetSelector.add(3, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal(this, IronGolemEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createElderAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.27D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.75D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
    }

    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            if (this.isImmobile()) {
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0D);
            } else {
                double d = this.getTarget() != null ? 0.35D : 0.3D;
                double e = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getBaseValue();
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(MathHelper.lerp(0.1D, e, d));
            }

            if (this.horizontalCollision && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                boolean bl = false;
                Box box = this.getBoundingBox().expand(0.2D);
                Iterator var8 = BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ)).iterator();

                label60:
                while(true) {
                    BlockPos blockPos;
                    Block block;
                    do {
                        if (!var8.hasNext()) {
                            if (!bl && this.onGround) {
                                this.jump();
                            }
                            break label60;
                        }

                        blockPos = (BlockPos)var8.next();
                        BlockState blockState = this.world.getBlockState(blockPos);
                        block = blockState.getBlock();
                    } while(!(block instanceof LeavesBlock));

                    bl = this.world.breakBlock(blockPos, true, this) || bl;
                }
            }

            if (this.attackTick > 0) {
                --this.attackTick;
            }

            if (this.stunTick > 0) {
                --this.stunTick;
                this.spawnStunnedParticles();
                if (this.stunTick == 0) {
                    this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
                }
            }

        }
    }

    private void spawnStunnedParticles() {
        if (this.random.nextInt(6) == 0) {
            double d = this.getX() - (double)this.getWidth() * Math.sin((double)(this.bodyYaw * 0.017453292F)) + (this.random.nextDouble() * 0.6D - 0.3D);
            double e = this.getY() + (double)this.getHeight() - 0.3D;
            double f = this.getZ() + (double)this.getWidth() * Math.cos((double)(this.bodyYaw * 0.017453292F)) + (this.random.nextDouble() * 0.6D - 0.3D);
            this.world.addParticle(ParticleTypes.SPIT, d, e, f, 0.4980392156862745D, 0.5137254901960784D, 0.5725490196078431D);
        }

    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("AttackTick", this.attackTick);
        nbt.putInt("StunTick", this.stunTick);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.attackTick = nbt.getInt("AttackTick");
        this.stunTick = nbt.getInt("StunTick");
    }

    protected EntityNavigation createNavigation(World world) {
        return new ElderEntity.ElderNavigation(this, world);
    }

    protected boolean isImmobile() {
        return super.isImmobile() || this.attackTick > 0 || this.stunTick > 0;
    }

    public boolean canSee(Entity entity) {
        return this.stunTick <= 0 && super.canSee(entity);
    }

    public void handleStatus(byte status) {
        if (status == 4) {
            this.attackTick = 10;
            this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        } else if (status == 39) {
            this.stunTick = 40;
        }

        super.handleStatus(status);
    }

    public int getAttackTick() {
        return this.attackTick;
    }

    public int getStunTick() {
        return this.stunTick;
    }

    public boolean tryAttack(Entity target) {
        this.attackTick = 10;
        this.world.sendEntityStatus(this, (byte)4);
        this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        return super.tryAttack(target);
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RAVAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_RAVAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RAVAGER_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15F, 1.0F);
    }

    private <E extends IAnimatable> PlayState basicMovement(AnimationEvent<E> event) {
        if (this.attackTick > 0) {
            // Replace with attack animation when ready
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elder.idle", true));
        } else if (event.getLimbSwingAmount() > 0.1F) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elder.move", true));
        } else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elder.idle", true));
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

    private static class ElderNavigation extends MobNavigation {
        public ElderNavigation(MobEntity mobEntity, World world) {
            super(mobEntity, world);
        }

        protected PathNodeNavigator createPathNodeNavigator(int range) {
            this.nodeMaker = new ElderEntity.PathNodeMaker();
            return new PathNodeNavigator(this.nodeMaker, range);
        }
    }

    static class PathNodeMaker extends LandPathNodeMaker {
        PathNodeMaker() {
        }

        protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors, BlockPos pos, PathNodeType type) {
            return type == PathNodeType.LEAVES ? PathNodeType.OPEN : super.adjustNodeType(world, canOpenDoors, canEnterOpenDoors, pos, type);
        }
    }
}
