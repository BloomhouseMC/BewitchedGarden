package com.bloomhousemc.bewitchedgarden.common;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = BewitchedGarden.MODID)
public class BGConfig implements ConfigData {
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBackpack = true;
}
