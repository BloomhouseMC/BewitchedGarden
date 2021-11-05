package com.bloomhousemc.bewitchedgarden.client.renderer;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.client.model.HeavensGateBlockModel;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.HeavensGateBlockEntity;
import com.bloomhousemc.bewitchedgarden.mixin.RenderLayerAccessor;
import net.minecraft.block.DoorBlock;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class HeavensGateBlockEntityRenderer extends GeoBlockRenderer<HeavensGateBlockEntity> {
    public static Shader renderTypeCustomShader;
    private static final RenderLayer.MultiPhase GATE = RenderLayerAccessor.callOf("end_portal", VertexFormats.POSITION, VertexFormat.DrawMode.QUADS, 256, false, false, RenderLayer.MultiPhaseParameters.builder()
    .shader(new RenderPhase.Shader(() -> renderTypeCustomShader))
    .texture(RenderPhase.Textures.create()
    .add(EndPortalBlockEntityRenderer.SKY_TEXTURE, false, false)
    .add(EndPortalBlockEntityRenderer.PORTAL_TEXTURE, false, false)
    .build()).build(false));

    public HeavensGateBlockEntityRenderer() {
        super(new HeavensGateBlockModel());
    }

    @Override
    public RenderLayer getRenderType(HeavensGateBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(HeavensGateBlockEntity heavensGateBlockEntity, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.render(heavensGateBlockEntity, partialTicks, stack, bufferIn, packedLightIn);
        Matrix4f matrix = stack.peek().getModel();
        VertexConsumer vertexConsumer = bufferIn.getBuffer(GATE);
        heavensGateBlockEntity.getCachedState().get(Properties.HORIZONTAL_FACING);
        switch (heavensGateBlockEntity.getCachedState().get(Properties.HORIZONTAL_FACING)) {
            case NORTH -> this.renderSide(matrix, vertexConsumer, 1.5F, -0.5F, 0.2F, 3.8F, 0.75F, 0.75F, 0.75F, 0.75F);
            case SOUTH -> this.renderSide(matrix, vertexConsumer, -0.5F, 1.5F, 0.2F, 3.8F, 0.25F, 0.25F, 0.25F, 0.25F);
            case EAST -> this.renderSide(matrix, vertexConsumer, 0.25F, 0.25F, 0.2F, 3.8F, 1.5F, -0.5F, -0.5F, 1.5F);
            case WEST -> this.renderSide(matrix, vertexConsumer, 0.75F, 0.75F, 0.2F, 3.8F, -0.5F, 1.5F, 1.5F, -0.5F);
        }
    }
    private void renderSide(Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4) {
        vertices.vertex(model, x1, y1, z1).next();
        vertices.vertex(model, x2, y1, z2).next();
        vertices.vertex(model, x2, y2, z3).next();
        vertices.vertex(model, x1, y2, z4).next();
    }
}