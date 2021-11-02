package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.client.renderer.BasePlantEntityRenderer;
import com.bloomhousemc.bewitchedgarden.client.renderer.HerbologistEntityRenderer;
import com.bloomhousemc.bewitchedgarden.client.renderer.SausageBlockEntityRenderer;
import com.bloomhousemc.bewitchedgarden.client.renderer.WispBlockEntityRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.entity.EntityType;

public class BGRendering {
    public static void init() {
        //Entities
        EntityRendererRegistry.INSTANCE.register(BGEntities.HERBOLOGIST, HerbologistEntityRenderer::new);

        //Plants
        EntityRendererRegistry.INSTANCE.register(BGEntities.JUPITER_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.VENUS_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.SATURN_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.ELDER_ENTITY, BasePlantEntityRenderer::new);

        //LayerMaps
        BlockRenderLayerMap.INSTANCE.putBlock(BGObjects.LUNARIAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BGObjects.SNARE, BGObjects.FIERY_SNARE, BGObjects.NIGHT_SNARE, BGObjects.TELEPORTATION_SNARE);
        BlockRenderLayerMap.INSTANCE.putBlock(BGObjects.MINT, RenderLayer.getCutout());

        //BlockEntity
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.SAUSAGE_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new SausageBlockEntityRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.WISP_BLOCK_ENTITY, ctx -> new WispBlockEntityRenderer());
    }
}
