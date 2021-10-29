package dev.mrsterner.witcheskitchen.common.registry;

import dev.mrsterner.witcheskitchen.client.render.HerbologistEntityRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class WKRendering {
    public static void init() {
        EntityRendererRegistry.INSTANCE.register(WKEntities.HERBOLOGIST, HerbologistEntityRenderer::new);
    }
}
