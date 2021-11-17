package com.bloomhousemc.bewitchedgarden.client.renderer.staff;

import com.bloomhousemc.bewitchedgarden.client.model.staff.JuniperStaffModel;
import com.bloomhousemc.bewitchedgarden.client.model.staff.PurityStaffModel;
import com.bloomhousemc.bewitchedgarden.common.items.staff.JuniperStaffItem;
import com.bloomhousemc.bewitchedgarden.common.items.staff.PurityStaffItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class JuniperStaffRenderer extends GeoItemRenderer<JuniperStaffItem> {
    public JuniperStaffRenderer() {
        super(new JuniperStaffModel());
    }

    @Override
    public RenderLayer getRenderType(JuniperStaffItem animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}