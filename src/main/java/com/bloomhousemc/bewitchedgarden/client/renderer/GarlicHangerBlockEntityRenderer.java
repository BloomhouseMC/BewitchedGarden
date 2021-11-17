package com.bloomhousemc.bewitchedgarden.client.renderer;

import com.bloomhousemc.bewitchedgarden.client.model.GarlicHangerBlockModel;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.GarlicHangerBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class GarlicHangerBlockEntityRenderer extends GeoBlockRenderer<GarlicHangerBlockEntity> {
    public GarlicHangerBlockEntityRenderer() {
        super(new GarlicHangerBlockModel());
    }

    @Override
    public RenderLayer getRenderType(GarlicHangerBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}