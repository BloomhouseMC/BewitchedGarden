package dev.mrsterner.bewitchedgarden;

import dev.mrsterner.bewitchedgarden.common.events.BGEvents;
import dev.mrsterner.bewitchedgarden.common.registry.BGEntities;
import dev.mrsterner.bewitchedgarden.common.registry.BGObjects;
import dev.mrsterner.bewitchedgarden.common.registry.BGStatusEffects;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BewitchedGarden implements ModInitializer {
	public static final String MODID = "witcheskitchen";
	public static final ItemGroup WITCHESKITCHEN_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(BGObjects.VENUS_POISON));

	@Override
	public void onInitialize() {
		BGObjects.init();
		BGEntities.init();
		BGEvents.init();
		BGStatusEffects.init();

	}
}
