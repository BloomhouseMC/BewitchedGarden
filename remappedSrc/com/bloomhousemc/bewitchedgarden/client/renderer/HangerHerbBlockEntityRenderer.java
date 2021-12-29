package com.bloomhousemc.bewitchedgarden.client.renderer;

import com.bloomhousemc.bewitchedgarden.client.model.HangerHerbBlockModel;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.HangerHerbBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class HangerHerbBlockEntityRenderer extends GeoBlockRenderer<HangerHerbBlockEntity> {
    public HangerHerbBlockEntityRenderer() {
        super(new HangerHerbBlockModel());
    }

    @Override
    public RenderLayer getRenderType(HangerHerbBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}