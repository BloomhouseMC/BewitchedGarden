package com.bloomhousemc.bewitchedgarden.common.entity.player;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class BackpackEntity extends LivingEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    private final DefaultedList<ItemStack> handItems;
    private final DefaultedList<ItemStack> armorItems;

    public BackpackEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.handItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
        this.armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0)
        .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, Double.MAX_VALUE);
    }



    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.isSneaking() && world.isClient){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.player.sneak", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.player.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return switch (slot.getType()) {
            case HAND -> this.handItems.get(slot.getEntitySlotId());
            case ARMOR -> this.armorItems.get(slot.getEntitySlotId());
        };
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }
}