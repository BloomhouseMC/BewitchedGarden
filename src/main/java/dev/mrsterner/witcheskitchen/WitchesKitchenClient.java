package dev.mrsterner.witcheskitchen;

import dev.mrsterner.witcheskitchen.client.renderer.SausageBlockEntityRenderer;
import dev.mrsterner.witcheskitchen.common.registry.WKObjects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

public class WitchesKitchenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockEntityRendererRegistry.INSTANCE.register(WKObjects.SAUSAGE_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new SausageBlockEntityRenderer());
    }
}
