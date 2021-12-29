package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.client.screen.BackpackScreen;
import com.bloomhousemc.bewitchedgarden.common.screen.BackpackScreenHandler;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.bloomhousemc.bewitchedgarden.BewitchedGarden.MODID;

public class BGScreens {
    public static final ScreenHandlerType<BackpackScreenHandler> BACKPACK_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier(MODID, "backpack"), (syncId, inventory, buf) ->
        new BackpackScreenHandler(syncId, inventory, buf.readBlockPos(), buf.readBoolean()));

    public static void initClient(){
        ScreenRegistry.register(BGScreens.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);
    }
}
