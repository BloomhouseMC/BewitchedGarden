package dev.mrsterner.witcheskitchen;


import dev.mrsterner.witcheskitchen.client.render.BasePlantEntityRenderer;
import dev.mrsterner.witcheskitchen.client.renderer.SausageBlockEntityRenderer;
import dev.mrsterner.witcheskitchen.common.registry.WKEntities;
import dev.mrsterner.witcheskitchen.common.registry.WKObjects;
import dev.mrsterner.witcheskitchen.common.registry.WKRendering;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.entity.EntityType;

public class WitchesKitchenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
         WKRendering.init();

        BlockEntityRendererRegistry.INSTANCE.register(WKObjects.SAUSAGE_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new SausageBlockEntityRenderer());
        //EntityRendererRegistry.INSTANCE.register(WKEntities.VENUS_ENTITY, BasePlantEntityRenderer::new);


        for(EntityType entityType : WKEntities.ENTITY_TYPES.keySet()){
            EntityRendererRegistry.INSTANCE.register(entityType, BasePlantEntityRenderer::new);
        }
    }
}
