package dev.mrsterner.witcheskitchen.common.events;

import dev.mrsterner.witcheskitchen.common.entity.VenusEntity;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WKEvents {
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
                }
            }
            return ActionResult.PASS;
        });
    }
}
