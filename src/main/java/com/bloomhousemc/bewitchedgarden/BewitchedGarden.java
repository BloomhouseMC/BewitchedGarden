package com.bloomhousemc.bewitchedgarden;

import com.bloomhousemc.bewitchedgarden.common.components.EffigyComponent;
import com.bloomhousemc.bewitchedgarden.common.entity.EffigyEntity;
import com.bloomhousemc.bewitchedgarden.common.registry.BGStatusEffects;
import com.bloomhousemc.bewitchedgarden.common.events.BGEvents;
import com.bloomhousemc.bewitchedgarden.common.registry.BGEntities;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.bloomhousemc.bewitchedgarden.common.world.BGWorldState;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class BewitchedGarden implements ModInitializer {
	public static final String MODID = "bewitchedgarden";
	public static final ItemGroup BEWITCHEDGARDEN_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(BGObjects.VENUS_POISON));

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		BGObjects.init();
		BGEntities.init();
		BGEvents.init();
		BGStatusEffects.init();



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



	public static EntityType<?> getEffigy(PlayerEntity player) {
		if (!player.world.isClient) {
			BGWorldState bgWorldState = BGWorldState.get(player.world);
			for (Pair<UUID, NbtCompound> pair : bgWorldState.effigies) {
				if (player.getUuid().equals(pair.getLeft())) {
					return Registry.ENTITY_TYPE.get(new Identifier(pair.getRight().getString("id")));
				}
			}
		}
		return null;
	}
}
