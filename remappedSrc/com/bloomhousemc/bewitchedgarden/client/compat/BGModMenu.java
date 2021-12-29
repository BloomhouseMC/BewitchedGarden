package com.bloomhousemc.bewitchedgarden.client.compat;

import com.bloomhousemc.bewitchedgarden.common.BGConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;

public class BGModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) screen -> AutoConfig.getConfigScreen(BGConfig.class, screen).get();

    }
}