package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.entity.EffigyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EffigyEntityModel extends AnimatedGeoModel<EffigyEntity> {

    @Override
    public Identifier getAnimationFileLocation(EffigyEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/effigy.animation.json");
    }

    @Override
    public Identifier getModelLocation(EffigyEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "geo/effigy.geo.json");
    }

    @Override
    public Identifier getTextureLocation(EffigyEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/entity/effigy.png");
    }
}