package com.bloomhousemc.bewitchedgarden.client.renderer;

import com.bloomhousemc.bewitchedgarden.client.model.LeafletEntityModel;
import com.bloomhousemc.bewitchedgarden.common.entity.LeafletEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LeafletEntityRenderer extends GeoEntityRenderer<LeafletEntity> {

    public LeafletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LeafletEntityModel());
    }
}
