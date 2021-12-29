package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.entity.LeafletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LeafletEntityModel extends AnimatedGeoModel<LeafletEntity> {

    @Override
    public Identifier getAnimationFileLocation(LeafletEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/leaflet.animation.json");
    }

    @Override
    public Identifier getModelLocation(LeafletEntity animatable) {
        return new Identifier(BewitchedGarden.MODID, "geo/leaflet.geo.json");
    }

    @Override
    public Identifier getTextureLocation(LeafletEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/entity/leaflet.png");
    }
}
