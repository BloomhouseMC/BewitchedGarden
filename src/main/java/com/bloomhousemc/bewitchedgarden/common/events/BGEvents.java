package com.bloomhousemc.bewitchedgarden.common.events;

import com.bloomhousemc.bewitchedgarden.common.components.EffigyComponent;
import com.bloomhousemc.bewitchedgarden.common.entity.EffigyEntity;
import com.bloomhousemc.bewitchedgarden.common.entity.VenusEntity;
import com.bloomhousemc.bewitchedgarden.common.world.BGWorldState;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

import static com.bloomhousemc.bewitchedgarden.common.util.BGUtils.getEffigy;

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

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(getEffigy(player) == null && player.getStackInHand(hand).getItem() instanceof TaglockItem && entity instanceof EffigyEntity effigyEntity){
                ItemStack taglock = player.getStackInHand(hand);
                UUID ownerUUID = TaglockItem.getTaglockUUID(taglock);
                NbtCompound entityCompound = new NbtCompound();
                effigyEntity.saveSelfNbt(entityCompound);
                entityCompound.putUuid("Owner", ownerUUID);
                if(entityCompound.contains("Owner") && player.getUuid().equals(entityCompound.getUuid("Owner"))){
                    BGWorldState bgWorldState = new BGWorldState();
                    NbtCompound effigiesCompound = new NbtCompound();
                    EffigyComponent.get(effigyEntity).setOwner(ownerUUID);
                    effigiesCompound.putUuid("UUID", entityCompound.getUuid("UUID"));
                    effigiesCompound.putString("id", Registry.ENTITY_TYPE.getId(effigyEntity.getType()).toString());
                    bgWorldState.effigies.add(new Pair<>(player.getUuid(), effigiesCompound));
                    bgWorldState.markDirty();
                }
            }
            return ActionResult.PASS;
        });
    }
}
