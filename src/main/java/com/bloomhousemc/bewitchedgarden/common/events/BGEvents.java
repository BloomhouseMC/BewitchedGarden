package com.bloomhousemc.bewitchedgarden.common.events;

import com.bloomhousemc.bewitchedgarden.common.entity.VenusEntity;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.UUID;

public class BGEvents {
    public static void init(){
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(world.isClient() || !Hand.MAIN_HAND.equals(hand)){
                return ActionResult.PASS;
            }
            ItemStack itemStack = player.getStackInHand(hand);
            if(player.getStackInHand(hand).isOf(itemStack.getItem())){
                if(hitResult.getEntity() instanceof VenusEntity venusEntity){
                    UUID owner = TaglockItem.getTaglockUUID(itemStack);
                    venusEntity.setOwner(owner);
                    venusEntity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,0.5F,1);
                }
            }
            return ActionResult.PASS;
        });
    }
}
