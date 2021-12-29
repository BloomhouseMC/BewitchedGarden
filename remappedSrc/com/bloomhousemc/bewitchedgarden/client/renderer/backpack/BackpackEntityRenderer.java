package com.bloomhousemc.bewitchedgarden.client.renderer.backpack;

import com.bloomhousemc.bewitchedgarden.client.model.backpack.BackpackEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BackpackEntityRenderer<T extends LivingEntity & IAnimatable> extends GeoEntityRenderer<T> {
    public BackpackEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BackpackEntityModel<>());
    }

    @Override
    public RenderLayer getRenderType(T animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
