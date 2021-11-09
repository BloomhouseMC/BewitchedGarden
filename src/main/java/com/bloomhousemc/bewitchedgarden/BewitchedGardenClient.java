package com.bloomhousemc.bewitchedgarden;


import com.bloomhousemc.bewitchedgarden.client.shader.ToxicShader;
import com.bloomhousemc.bewitchedgarden.common.registry.BGEntities;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.bloomhousemc.bewitchedgarden.common.registry.BGRendering;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class BewitchedGardenClient implements ClientModInitializer {
    ToxicShader toxicShader = new ToxicShader();
    @Override
    public void onInitializeClient() {
        BGRendering.init();
        toxicShader.init();
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->
        view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) :
        FoliageColors.getDefaultColor(), BGObjects.BLOODROOT
        );
    }
}
