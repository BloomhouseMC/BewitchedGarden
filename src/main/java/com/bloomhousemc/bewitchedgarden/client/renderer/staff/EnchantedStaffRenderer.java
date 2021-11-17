package com.bloomhousemc.bewitchedgarden.client.renderer.staff;

import com.bloomhousemc.bewitchedgarden.client.model.staff.EnchantedStaffModel;
import com.bloomhousemc.bewitchedgarden.common.items.staff.EnchantedStaffItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class EnchantedStaffRenderer extends GeoItemRenderer<EnchantedStaffItem> {
    public EnchantedStaffRenderer() {
        super(new EnchantedStaffModel());
    }


    @Override
    public RenderLayer getRenderType(EnchantedStaffItem animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}