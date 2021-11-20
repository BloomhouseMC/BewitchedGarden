package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.HangerHerbBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.MoonflowerBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MoonflowerModel extends AnimatedGeoModel<MoonflowerBlockEntity> {

    @Override
    public Identifier getAnimationFileLocation(MoonflowerBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/moonflower.animation.json");
    }

    @Override
    public Identifier getModelLocation(MoonflowerBlockEntity animatable) {
        return new Identifier(BewitchedGarden.MODID, "geo/moonflower.geo.json");
    }

    @Override
    public Identifier getTextureLocation(MoonflowerBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/block/moonflower.png");
    }
}