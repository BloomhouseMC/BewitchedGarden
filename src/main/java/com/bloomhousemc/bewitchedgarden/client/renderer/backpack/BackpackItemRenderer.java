package com.bloomhousemc.bewitchedgarden.client.renderer.backpack;

import com.bloomhousemc.bewitchedgarden.client.model.backpack.BackpackItemModel;
import com.bloomhousemc.bewitchedgarden.common.items.BackpackItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BackpackItemRenderer extends GeoItemRenderer<BackpackItem> {
    public BackpackItemRenderer() {
        super(new BackpackItemModel());
    }

    @Override
    public RenderLayer getRenderType(BackpackItem animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}