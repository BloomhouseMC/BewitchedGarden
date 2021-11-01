package com.bloomhousemc.bewitchedgarden.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.annotation.Debug;
import net.minecraft.world.World;

public class SaturnEntity extends BasePlantEntity implements InventoryOwner {
    private final SimpleInventory inventory = new SimpleInventory(1);
    public SaturnEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("Inventory", inventory.toNbtList());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        inventory.readNbtList(nbt.getList("Inventory", 10));
    }

    @Override
    @Debug
    public Inventory getInventory() {
        return inventory;
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ActionResult actionResult = super.interactMob(player, hand);
        if(Hand.MAIN_HAND.equals(hand)){
            if(inventory.getStack(0).isEmpty() && !player.getStackInHand(hand).isEmpty()){
                inventory.addStack(player.getStackInHand(hand).split(1));
                this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.4F,0.7F);
            }
            else if (player.getStackInHand(hand).isEmpty()){
                dropItem(this.getInventory().getStack(0).getItem());
                getInventory().setStack(0, ItemStack.EMPTY);
                this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F,1);
            }
        }
        return actionResult;
    }

    @Override
    public void onDeath(DamageSource source) {
        dropItem(this.getInventory().getStack(0).getItem());
        super.onDeath(source);
    }
}
