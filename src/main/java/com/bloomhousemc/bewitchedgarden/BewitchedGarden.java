package com.bloomhousemc.bewitchedgarden;

import com.bloomhousemc.bewitchedgarden.common.registry.*;
import com.bloomhousemc.bewitchedgarden.common.events.BGEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
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
	public static final Logger LOGGER = LogManager.getLogger("bewitchedgarden");

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		BGObjects.init();
		BGEntities.init();
		BGEvents.init();
		BGStatusEffects.init();
		BGRitualFunctions.init();
		BGWorld.init();
		SOUNDS = new BGSounds();
	}
}
