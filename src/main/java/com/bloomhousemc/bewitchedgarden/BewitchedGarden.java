package com.bloomhousemc.bewitchedgarden;

import com.bloomhousemc.bewitchedgarden.common.BGConfig;
import com.bloomhousemc.bewitchedgarden.common.network.EquipBackpackPacket;
import com.bloomhousemc.bewitchedgarden.common.network.OpenBackpackScreenPacket;
import com.bloomhousemc.bewitchedgarden.common.network.PlaceBackpackPacket;
import com.bloomhousemc.bewitchedgarden.common.registry.*;
import com.bloomhousemc.bewitchedgarden.common.events.BGEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class BewitchedGarden implements ModInitializer {
	public static final String MODID = "bewitchedgarden";
	public static final ItemGroup BEWITCHEDGARDEN_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(BGObjects.BLOODROOT_ITEM));

	public static BGSounds SOUNDS;
	public static BGConfig config;
	public static final Logger LOGGER = LogManager.getLogger("bewitchedgarden");

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		AutoConfig.register(BGConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(BGConfig.class).getConfig();
		BGConditions.init();
		ServerPlayNetworking.registerGlobalReceiver(PlaceBackpackPacket.ID, PlaceBackpackPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(OpenBackpackScreenPacket.ID, OpenBackpackScreenPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(EquipBackpackPacket.ID, EquipBackpackPacket::handle);
		BGObjects.init();
		BGEntities.init();
		BGEvents.init();
		BGStatusEffects.init();
		BGRitualFunctions.init();
		//BGWorld.init();
		SOUNDS = new BGSounds();


	}
	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
