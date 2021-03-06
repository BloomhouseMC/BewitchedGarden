package com.bloomhousemc.bewitchedgarden;


import com.bloomhousemc.bewitchedgarden.client.shader.ToxicShader;
import com.bloomhousemc.bewitchedgarden.common.registry.BGRendering;
import com.bloomhousemc.bewitchedgarden.common.registry.BGScreens;
import net.fabricmc.api.ClientModInitializer;

public class BewitchedGardenClient implements ClientModInitializer {
    ToxicShader toxicShader = new ToxicShader();
    @Override
    public void onInitializeClient() {
        BGRendering.init();
        BGScreens.initClient();

        toxicShader.init();

    }
}
