package dev.mrsterner.witcheskitchen.client.render;

import dev.mrsterner.witcheskitchen.WitchesKitchen;
import dev.mrsterner.witcheskitchen.common.entity.BasePlantEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@Environment(EnvType.CLIENT)
public class BasePlantGlowFeatureRenderer extends GeoLayerRenderer<BasePlantEntity> {
    public String getEntity(BasePlantEntity plantEntity){
        return Registry.ENTITY_TYPE.getKey(plantEntity.getType()).get().getValue().getPath();
    }
    private final IGeoRenderer<BasePlantEntity> renderer;
    public BasePlantGlowFeatureRenderer(BasePlantEntityRenderer entityRendererIn, BasePlantEntityRenderer.BasePlantGlowRenderer revenantEyesRenderer) {
        super(entityRendererIn);
        this.renderer = revenantEyesRenderer;
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, BasePlantEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        /*
        renderer.render(getEntityModel().getModel(new Identifier(WitchesKitchen.MODID, "geo/"+getEntity(entitylivingbaseIn)+".geo.json")),
        entitylivingbaseIn,
        partialTicks,
        RenderLayer.getEyes(new Identifier(WitchesKitchen.MODID,"textures/entity/"+getEntity(entitylivingbaseIn)+"_glow_1.png")),
        matrixStackIn,
        bufferIn,
        bufferIn.getBuffer(RenderLayer.getEyes(new Identifier(WitchesKitchen.MODID,"textures/entity/"+getEntity(entitylivingbaseIn)+"_glow_1.png"))),
        packedLightIn,
        OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        System.out.println(getEntity(entitylivingbaseIn));
        renderer.render(getEntityModel().getModel(new Identifier(WitchesKitchen.MODID, "geo/"+getEntity(entitylivingbaseIn)+".geo.json")),
        entitylivingbaseIn,
        partialTicks,
        RenderLayer.getEyes(new Identifier(WitchesKitchen.MODID,"textures/entity/"+getEntity(entitylivingbaseIn)+"_glow_2.png")),
        matrixStackIn,
        bufferIn,
        bufferIn.getBuffer(RenderLayer.getEyes(new Identifier(WitchesKitchen.MODID,"textures/entity/"+getEntity(entitylivingbaseIn)+"_glow_2.png"))),
        packedLightIn,
        OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

*/
    }
}
