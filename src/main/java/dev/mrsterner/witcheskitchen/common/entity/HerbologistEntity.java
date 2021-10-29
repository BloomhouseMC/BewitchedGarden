package dev.mrsterner.witcheskitchen.common.entity;

import dev.mrsterner.witcheskitchen.common.util.HerbologistTrades;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HerbologistEntity extends MerchantEntity {

    public HerbologistEntity(EntityType<? extends HerbologistEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new HoldInHandsGoal(this, getRandomHeldItem(), SoundEvents.ENTITY_WANDERING_TRADER_REAPPEARED, (entity) -> {
            return this.world.isDay();
        }));
        this.goalSelector.add(1, new HoldInHandsGoal(this, new ItemStack(BWObjects.GLOWING_BRAMBLE), SoundEvents.ENTITY_WANDERING_TRADER_REAPPEARED, (entity) -> {
            return this.world.isNight();
        }));
        this.goalSelector.add(2, new FleeEntityGoal(this, HostileEntity.class, 8.0F, 0.5D, 0.5D));
        this.goalSelector.add(3, new EscapeDangerGoal(this, 0.5D));
        this.goalSelector.add(3, new StopFollowingCustomerGoal(this));
        this.goalSelector.add(3, new LookAtCustomerGoal(this));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.35D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, WitchEntity.class, 8.0F));
    }

    public static DefaultAttributeContainer.Builder createHerbologistAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    private ItemStack getRandomHeldItem() {
        if (this.random.nextFloat() <= 0.3F) {
            return new ItemStack(BWObjects.BELLADONNA);
        } else if (this.random.nextFloat() <= 0.3F) {
            return new ItemStack(BWObjects.GARLIC);
        }
        return new ItemStack(BWObjects.MANDRAKE_ROOT);
    }

    protected void afterUsing(TradeOffer offer) {
        if (offer.shouldRewardPlayerExperience()) {
            int i = 3 + this.random.nextInt(4);
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    protected void fillRecipes() {
        TradeOffers.Factory[] factorys = (TradeOffers.Factory[]) HerbologistTrades.HERBOLOGIST_TRADES.get(1);
        TradeOffers.Factory[] factorys2 = (TradeOffers.Factory[])HerbologistTrades.HERBOLOGIST_TRADES.get(2);
        if (factorys != null && factorys2 != null) {
            TradeOfferList tradeOfferList = this.getOffers();
            this.fillRecipesFromPool(tradeOfferList, factorys, 5);
            int i = this.random.nextInt(factorys2.length);
            TradeOffers.Factory factory = factorys2[i];
            TradeOffer tradeOffer = factory.create(this, this.random);
            if (tradeOffer != null) {
                tradeOfferList.add(tradeOffer);
            }

        }
    }

    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public boolean isLeveledMerchant() {
        return false;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isOf(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.hasCustomer() && !this.isBaby()) {
            if (hand == Hand.MAIN_HAND) {
                player.incrementStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                return ActionResult.success(this.world.isClient);
            } else {
                if (!this.world.isClient) {
                    this.setCurrentCustomer(player);
                    this.sendOffers(player, this.getDisplayName(), 1);
                }

                return ActionResult.success(this.world.isClient);
            }
        } else {
            return super.interactMob(player, hand);
        }
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return this.hasCustomer() ? SoundEvents.ENTITY_WITCH_CELEBRATE : SoundEvents.ENTITY_WITCH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WITCH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITCH_DEATH;
    }
}
