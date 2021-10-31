package dev.mrsterner.witcheskitchen.common.registry;

import dev.mrsterner.witcheskitchen.client.renderer.HerbologistEntityRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class WKRendering {
    public static void init() {
        EntityRendererRegistry.INSTANCE.register(WKEntities.HERBOLOGIST, HerbologistEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(WKObjects.LUNARIAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WKObjects.TELEPORTATION_SNARE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WKObjects.MINT, RenderLayer.getCutout());
    }
}
