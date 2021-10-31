package dev.mrsterner.witcheskitchen.common.registry;

import dev.mrsterner.witcheskitchen.client.render.BasePlantEntityRenderer;
import dev.mrsterner.witcheskitchen.client.renderer.HerbologistEntityRenderer;
import dev.mrsterner.witcheskitchen.client.renderer.SausageBlockEntityRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

public class WKRendering {
    public static void init() {
        //Entities
        EntityRendererRegistry.INSTANCE.register(WKEntities.HERBOLOGIST, HerbologistEntityRenderer::new);

        //Plants
        EntityRendererRegistry.INSTANCE.register(WKEntities.JUPITER_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(WKEntities.VENUS_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(WKEntities.SATURN_ENTITY, BasePlantEntityRenderer::new);

        //LayerMaps
        BlockRenderLayerMap.INSTANCE.putBlock(WKObjects.LUNARIAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WKObjects.TELEPORTATION_SNARE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WKObjects.MINT, RenderLayer.getCutout());

        //BlockEntity
        BlockEntityRendererRegistry.INSTANCE.register(WKObjects.SAUSAGE_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new SausageBlockEntityRenderer());


    }
}
