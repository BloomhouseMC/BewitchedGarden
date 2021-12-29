package com.bloomhousemc.bewitchedgarden.mixin.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CraftingScreenHandler.class)
public interface CraftingScreenHandlerMixin {
    @Invoker("updateResult")
    static void callUpdateResult(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        throw new UnsupportedOperationException();
    }
}