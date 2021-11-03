package com.bloomhousemc.bewitchedgarden;


import com.bloomhousemc.bewitchedgarden.client.shader.ToxicShader;
import com.bloomhousemc.bewitchedgarden.common.registry.BGEntities;
import com.bloomhousemc.bewitchedgarden.common.registry.BGRendering;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class BewitchedGardenClient implements ClientModInitializer {
    ToxicShader toxicShader = new ToxicShader();
    @Override
    public void onInitializeClient() {
        BGRendering.init();
        toxicShader.init();
        EntityRendererRegistry.INSTANCE.register(BGEntities.MUTANDIS_ENTITY_ENTITY_TYPE, (context) -> new FlyingItemEntityRenderer(context));
    }
}
