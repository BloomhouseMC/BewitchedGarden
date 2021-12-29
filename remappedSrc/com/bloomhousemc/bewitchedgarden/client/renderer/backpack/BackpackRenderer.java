package com.bloomhousemc.bewitchedgarden.client.renderer.backpack;

import com.bloomhousemc.bewitchedgarden.client.model.backpack.BackpackModel;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.BackpackBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class BackpackRenderer extends GeoBlockRenderer<BackpackBlockEntity> {
    public BackpackRenderer() {
        super(new BackpackModel());
    }


    @Override
    public RenderLayer getRenderType(BackpackBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}