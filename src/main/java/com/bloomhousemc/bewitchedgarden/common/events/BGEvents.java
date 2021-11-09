package com.bloomhousemc.bewitchedgarden.common.events;

import com.bloomhousemc.bewitchedgarden.common.components.EffigyComponent;
import com.bloomhousemc.bewitchedgarden.common.entity.EffigyEntity;
import com.bloomhousemc.bewitchedgarden.common.entity.MuncherEntity;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

import java.util.UUID;


public class BGEvents {
    public static void init(){

        /**
         * @author - MrSterner
         * If a player has a taglock it applied the ownership in the taglock to the Venus entity.
         * Proceeds to play a sound and decrement the held itemstack by 1.
         *
         * @param  player the player who is holding the taglock.
         * @param  world to make sure we execute on server side.
         * @param  hand to get the players left and right hand.
         * @param  entity to check if the target is an instance of VenusEntity
         */
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(!world.isClient() && entity instanceof MuncherEntity muncherEntity && player.getStackInHand(hand).getItem() instanceof TaglockItem taglockItem){
                ItemStack itemStack = player.getStackInHand(hand);
                UUID owner = TaglockItem.getTaglockUUID(itemStack);
                muncherEntity.setOwner(owner);
                muncherEntity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1);
                taglockItem.getDefaultStack().decrement(1);
            }
            return ActionResult.PASS;
        });

        /**
         * @author - MrSterner
         * If the player doesnt have a bound effigy, binds the player on the used entity.
         * Checks if the playerEntity from the taglock has a effigy already,
         * if not, binds the effigy to the player with EffigyComponent.
         * Proceeds to play a sound and decrement the held itemstack by 1.
         *
         * @param  player the player who is holding the taglock.
         * @param  world to make sure we execute on server side and so we can get any player based on UUID alone.
         * @param  hand to get the players left and right hand to get the taglock and decrement stack.
         * @param  entity to check if the target is an instance of EffigyEntity
         */
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(!world.isClient && player.getStackInHand(hand).getItem() instanceof TaglockItem && entity instanceof EffigyEntity effigyEntity){
                ItemStack tagLock = player.getStackInHand(hand);
                UUID ownerUUID = TaglockItem.getTaglockUUID(tagLock);
                PlayerEntity playerEntity = world.getPlayerByUuid(ownerUUID);
                if(!EffigyComponent.get(playerEntity).getHasEffigy()){
                    EffigyComponent.get(playerEntity).setEffigy(effigyEntity.getUuid());
                    EffigyComponent.get(playerEntity).setHasEffigy(true);
                    effigyEntity.playSound(SoundEvents.BLOCK_ROOTS_BREAK, 3F, 1);
                    player.getStackInHand(hand).decrement(1);
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
