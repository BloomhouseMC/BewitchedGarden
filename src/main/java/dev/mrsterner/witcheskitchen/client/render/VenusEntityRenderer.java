package dev.mrsterner.witcheskitchen.client.render;

import dev.mrsterner.witcheskitchen.client.model.VenusEntityModel;
import dev.mrsterner.witcheskitchen.common.entity.VenusEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VenusEntityRenderer extends GeoEntityRenderer<VenusEntity> {
    public VenusEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new VenusEntityModel());
    }

    @Override
    public RenderLayer getRenderType(VenusEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }

}