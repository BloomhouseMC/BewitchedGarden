package com.bloomhousemc.bewitchedgarden.common.entity;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MuncherEntity extends BasePlantEntity {
    public MuncherEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getVariants() {
        return getVariantsStatic();
    }

    @Override
    public void tick() {
        if(getOwner() != null){
            //System.out.println(owner);
        }
        super.tick();
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
        if (dataTracker.get(VARIANT) != 0) {
            switch (world.getBiome(getBlockPos()).getCategory()) {
                case DESERT -> dataTracker.set(VARIANT, 3);
                case TAIGA -> dataTracker.set(VARIANT, 2);
                default -> dataTracker.set(VARIANT, 1);
            }
        }
        return data;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new PlantAttackGoal(this, 1.5D, false));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (target != null && target.getUuid() == getOwner()) {
            target = null;
        }
        super.setTarget(target);
    }

    @Override
    public void onDeath(DamageSource source) {
        if(world.getBlockState(this.getBlockPos()).isAir()){
            world.setBlockState(this.getBlockPos(), BGObjects.POISON_PUDDLE.getDefaultState());
        }
        super.onDeath(source);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    protected boolean hasShiny() {
        return true;
    }
    public static int getVariantsStatic() {
        return 4;
    }
}
