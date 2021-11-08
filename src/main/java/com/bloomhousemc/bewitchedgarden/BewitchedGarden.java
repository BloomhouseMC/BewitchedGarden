package com.bloomhousemc.bewitchedgarden;

import com.bloomhousemc.bewitchedgarden.common.events.BGEvents;
import com.bloomhousemc.bewitchedgarden.common.registry.BGEntities;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.bloomhousemc.bewitchedgarden.common.registry.BGStatusEffects;
import com.bloomhousemc.bewitchedgarden.common.registry.BGWorld;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;

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
		BGWorld.init();
	}
}
