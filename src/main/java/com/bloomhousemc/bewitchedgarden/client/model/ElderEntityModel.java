package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.entity.ElderEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ElderEntityModel extends AnimatedGeoModel<ElderEntity> {
    @Override
    public Identifier getAnimationFileLocation(ElderEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/elder.animation.json");
    }

    @Override
    public Identifier getModelLocation(ElderEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "geo/elder.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ElderEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/entity/plant/elder.png");
    }
}
