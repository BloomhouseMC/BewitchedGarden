package com.bloomhousemc.bewitchedgarden.client.renderer;

import com.bloomhousemc.bewitchedgarden.client.model.CruptidEntityModel;
import com.bloomhousemc.bewitchedgarden.common.entity.CruptidEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CruptidEntityRenderer extends GeoEntityRenderer<CruptidEntity> {

    public CruptidEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CruptidEntityModel());
    }
}
