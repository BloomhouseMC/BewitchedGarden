package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.entity.CruptidEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CruptidEntityModel extends AnimatedGeoModel<CruptidEntity> {

    @Override
    public Identifier getAnimationFileLocation(CruptidEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/cruptid.animation.json");
    }

    @Override
    public Identifier getModelLocation(CruptidEntity animatable) {
        return new Identifier(BewitchedGarden.MODID, "geo/cruptid.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CruptidEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/entity/cruptid.png");
    }
}
