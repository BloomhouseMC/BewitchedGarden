package dev.mrsterner.witcheskitchen;

import dev.mrsterner.witcheskitchen.common.entity.VenusEntity;
import dev.mrsterner.witcheskitchen.common.events.WKEvents;
import dev.mrsterner.witcheskitchen.common.registry.WKEntities;
import dev.mrsterner.witcheskitchen.common.registry.WKObjects;
import moriyashiine.bewitchment.common.item.TaglockItem;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import java.util.UUID;

public class WitchesKitchen implements ModInitializer {
	public static final String MODID = "witcheskitchen";
	public static final ItemGroup WITCHESKITCHEN_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(WKObjects.VENUS_POISON));

	@Override
	public void onInitialize() {
		WKObjects.init();
		WKEntities.init();
		WKEvents.init();

	}
}
