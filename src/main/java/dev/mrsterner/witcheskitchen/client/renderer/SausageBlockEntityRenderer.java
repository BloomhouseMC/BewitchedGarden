package dev.mrsterner.witcheskitchen.client.renderer;

import dev.mrsterner.witcheskitchen.client.model.SausageBlockModel;
import dev.mrsterner.witcheskitchen.common.blocks.blockentity.SausageBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class SausageBlockEntityRenderer extends GeoBlockRenderer<SausageBlockEntity> {
    public SausageBlockEntityRenderer() {
        super(new SausageBlockModel());
    }

    @Override
    public RenderLayer getRenderType(SausageBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}