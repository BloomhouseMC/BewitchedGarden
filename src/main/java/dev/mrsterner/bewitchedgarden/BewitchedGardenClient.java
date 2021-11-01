package dev.mrsterner.bewitchedgarden;


import dev.mrsterner.bewitchedgarden.client.shader.ToxicShader;
import dev.mrsterner.bewitchedgarden.common.registry.BGRendering;
import net.fabricmc.api.ClientModInitializer;

public class BewitchedGardenClient implements ClientModInitializer {
    ToxicShader toxicShader = new ToxicShader();
    @Override
    public void onInitializeClient() {
        BGRendering.init();
        toxicShader.init();
    }
}
