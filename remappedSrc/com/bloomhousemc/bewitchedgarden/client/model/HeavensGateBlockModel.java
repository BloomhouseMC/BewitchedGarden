package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.HeavensGateBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HeavensGateBlockModel extends AnimatedGeoModel<HeavensGateBlockEntity> {

    @Override
    public Identifier getAnimationFileLocation(HeavensGateBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/door.animation.json");
    }

    @Override
    public Identifier getModelLocation(HeavensGateBlockEntity animatable) {
        return new Identifier(BewitchedGarden.MODID, "geo/door.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HeavensGateBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/block/door.png");
    }
}