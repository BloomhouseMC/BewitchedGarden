package com.bloomhousemc.bewitchedgarden.common.entity;

import moriyashiine.bewitchment.common.block.entity.interfaces.TaglockHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class BasePlantEntity extends PathAwareEntity implements IAnimatable, Angerable, TaglockHolder {
    AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(BasePlantEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> STATE = DataTracker.registerData(BasePlantEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(1, 2);
    private UUID targetUuid;
    private UUID owner = null;
    private int attackTicksLeft;
    private final DefaultedList<ItemStack> taglockInventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    protected BasePlantEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
    public String getEntity(BasePlantEntity ghostEntity){
        return Registry.ENTITY_TYPE.getKey(ghostEntity.getType()).get().getValue().getPath();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0)
        .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, Double.MAX_VALUE);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        super.initGoals();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGER_TIME, 0);
        this.dataTracker.startTracking(STATE, 0);
    }

    public int getAttckingState() {
        return this.dataTracker.get(STATE);
    }

    public void setAttackingState(int time) {
        this.dataTracker.set(STATE, time);
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TIME, ticks);
    }

    @Override
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    public DefaultedList<ItemStack> getTaglockInventory() {
        return taglockInventory;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID uuid) {
        this.owner = uuid;
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.attackTicksLeft = 10;
        return super.tryAttack(target);
    }


    private <E extends IAnimatable> PlayState basicMovement(AnimationEvent<E> event) {
        if(this.dataTracker.get(STATE) >= 1){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation."+getEntity(this)+".attack", true));
        }else{
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation."+getEntity(this)+".idle", true));
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
    static class PlantAttackGoal extends MeleeAttackGoal {
        private final BasePlantEntity parentEntity;
        protected int cooldown = 0;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int updateCountdownTicks;
        private final boolean pauseWhenMobIdle;

        public PlantAttackGoal(VenusEntity venusEntity, double speed, boolean pauseWhenMobIdle) {
            super(venusEntity, speed, pauseWhenMobIdle);
            this.parentEntity = venusEntity;
            this.pauseWhenMobIdle = pauseWhenMobIdle;
        }

        @Override
        public boolean canStart() {
            return this.parentEntity.getTarget() != null && !this.parentEntity.getTarget().getUuid().equals(this.parentEntity.getOwner());
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isIdle();
            } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
                return false;
            }else if (this.parentEntity.getTarget() != null){
                if(this.parentEntity.getTarget().getUuid() == null){
                    return !this.parentEntity.getTarget().getUuid().equals(this.parentEntity.getOwner());
                }
            } else {
                return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
            }
            return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
        }

        @Override
        public void start() {
            super.start();
            this.parentEntity.setAttacking(true);
            this.cooldown = -1;
            this.parentEntity.setAttackingState(0);
        }

        @Override
        public void stop() {
            super.stop();
            this.parentEntity.setAttacking(false);
            this.parentEntity.setAttackingState(0);
            this.cooldown = -1;
        }

        @Override
        public void tick() {
            if(this.parentEntity.attackTicksLeft > 0){
                this.parentEntity.setAttackingState(1);
            }else{
                this.parentEntity.setAttackingState(0);
            }
            if (this.parentEntity.attackTicksLeft > 0) {
                --this.parentEntity.attackTicksLeft;
            }
            LivingEntity livingEntity = this.mob.getTarget();
            this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
            double d = this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
            if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity)) && this.updateCountdownTicks <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                this.targetX = livingEntity.getX();
                this.targetY = livingEntity.getY();
                this.targetZ = livingEntity.getZ();
                this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                if (d > 1024.0D) {
                    this.updateCountdownTicks += 10;
                } else if (d > 256.0D) {
                    this.updateCountdownTicks += 5;
                }
            }

            this.cooldown = Math.max(this.cooldown - 1, 0);
            //this.parentEntity.setAttackingState(1);
            this.attack(livingEntity, d);
        }
    }
}
