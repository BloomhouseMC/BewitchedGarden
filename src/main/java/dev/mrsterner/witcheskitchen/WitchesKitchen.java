package dev.mrsterner.witcheskitchen;

import dev.mrsterner.witcheskitchen.common.registry.WKEntities;
import dev.mrsterner.witcheskitchen.common.registry.WKObjects;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class WitchesKitchen implements ModInitializer {
	public static final String MODID = "witcheskitchen";
	public static final ItemGroup WITCHESKITCHEN_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(WKObjects.VENUS_POISON));

	@Override
	public void onInitialize() {
		WKObjects.init();
		WKEntities.init();

	}
}
