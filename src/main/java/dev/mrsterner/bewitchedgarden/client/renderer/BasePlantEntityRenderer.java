package dev.mrsterner.bewitchedgarden.client.renderer;

import dev.mrsterner.bewitchedgarden.client.model.BasePlantEntityModel;
import dev.mrsterner.bewitchedgarden.common.entity.BasePlantEntity;
import dev.mrsterner.bewitchedgarden.common.entity.SaturnEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BasePlantEntityRenderer extends GeoEntityRenderer<BasePlantEntity> {
    private ItemStack itemStack;
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

    @Override
    public void renderEarly(BasePlantEntity basePlantEntity, MatrixStack stackIn, float ticks, VertexConsumerProvider vertexConsumerProvider, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        if(basePlantEntity instanceof SaturnEntity saturnEntity){
            this.itemStack = saturnEntity.getInventory().getStack(0);
        }
        super.renderEarly(basePlantEntity, stackIn, ticks, vertexConsumerProvider, vertexBuilder, packedLightIn, packedOverlayIn, red,
        green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("handheld")) bone.setHidden(true);
        if (bone.getName().equals("handheld")) {
            stack.push();
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(0));
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
            stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
            stack.translate(0.0D, 2.5D, 0.0D);
            stack.scale(1.0f, 1.0f, 1.0f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND,
            packedLightIn, packedOverlayIn, stack, this.rtb, 0);
            stack.pop();
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}