package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.GarlicHangerBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.SausageBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GarlicHangerBlockModel extends AnimatedGeoModel<GarlicHangerBlockEntity> {

    @Override
    public Identifier getAnimationFileLocation(GarlicHangerBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/garlichanger.animation.json");
    }

    @Override
    public Identifier getModelLocation(GarlicHangerBlockEntity animatable) {
        return new Identifier(BewitchedGarden.MODID, "geo/garlichanger.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GarlicHangerBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/block/garlichanger.png");
    }
}