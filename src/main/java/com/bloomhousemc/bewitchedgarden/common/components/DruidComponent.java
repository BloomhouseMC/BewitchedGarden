package com.bloomhousemc.bewitchedgarden.common.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import net.minecraft.nbt.NbtCompound;
import org.lwjgl.system.CallbackI;

public class DruidComponent implements AutoSyncedComponent, ServerTickingComponent {

    @Override
    public void serverTick() {
    }

    @Override
    public void readFromNbt(NbtCompound tag) {

    }

    @Override
    public void writeToNbt(NbtCompound tag) {

    }
}
