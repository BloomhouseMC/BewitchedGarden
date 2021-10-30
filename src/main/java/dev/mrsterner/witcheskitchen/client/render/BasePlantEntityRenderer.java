package dev.mrsterner.witcheskitchen.client.render;

import dev.mrsterner.witcheskitchen.client.model.BasePlantEntityModel;
import dev.mrsterner.witcheskitchen.common.entity.BasePlantEntity;
import dev.mrsterner.witcheskitchen.common.entity.VenusEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BasePlantEntityRenderer extends GeoEntityRenderer<BasePlantEntity> {
    public BasePlantEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BasePlantEntityModel());
        this.addLayer(new BasePlantGlowFeatureRenderer(this, new BasePlantGlowRenderer(ctx, new BasePlantEntityModel())));
    }

    @Override
    public RenderLayer getRenderType(BasePlantEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }
    public static class BasePlantGlowRenderer extends GeoEntityRenderer<BasePlantEntity> {
        protected BasePlantGlowRenderer(EntityRendererFactory.Context ctx, AnimatedGeoModel<BasePlantEntity> modelProvider) {
            super(ctx, modelProvider);
        }
    }

}