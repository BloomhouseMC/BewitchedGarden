package dev.mrsterner.bewitchedgarden.common.registry;

import dev.mrsterner.bewitchedgarden.client.renderer.BasePlantEntityRenderer;
import dev.mrsterner.bewitchedgarden.client.renderer.HerbologistEntityRenderer;
import dev.mrsterner.bewitchedgarden.client.renderer.SausageBlockEntityRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

public class BGRendering {
    public static void init() {
        //Entities
        EntityRendererRegistry.INSTANCE.register(BGEntities.HERBOLOGIST, HerbologistEntityRenderer::new);

        //Plants
        EntityRendererRegistry.INSTANCE.register(BGEntities.JUPITER_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.VENUS_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.SATURN_ENTITY, BasePlantEntityRenderer::new);

        //LayerMaps
        BlockRenderLayerMap.INSTANCE.putBlock(BGObjects.LUNARIAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BGObjects.TELEPORTATION_SNARE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BGObjects.MINT, RenderLayer.getCutout());

        //BlockEntity
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.SAUSAGE_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new SausageBlockEntityRenderer());


    }
}
