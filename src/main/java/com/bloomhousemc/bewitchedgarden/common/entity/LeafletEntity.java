package com.bloomhousemc.bewitchedgarden.common.entity;

import com.bloomhousemc.bewitchedgarden.common.registry.BGTags;
import moriyashiine.bewitchment.common.registry.BWTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LeafletEntity extends TameableEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);

    public LeafletEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new TemptGoal(this, 0.3D, Ingredient.ofItems(Items.GRASS, Items.TALL_GRASS), false));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
        this.goalSelector.add(4, new EatGrassGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D, 1.0000001E-5F));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
    }

    public static DefaultAttributeContainer.Builder createLeafletAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24000000417232513D);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || itemStack.isOf(Items.GRASS) && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (this.isTamed()) {
                if (itemStack.isOf(Items.GRASS) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }
                    this.heal(2.0F);
                    this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                    return ActionResult.SUCCESS;
                }
            } else if (itemStack.isOf(Items.GRASS)) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget((LivingEntity)null);
                    this.setSitting(true);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }

                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
        }
    }

    private <E extends IAnimatable> PlayState basicMovement(AnimationEvent<E> event) {
        if (event.getLimbSwingAmount()>0.1F){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leaflet.move", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leaflet.idle", true));
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

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
