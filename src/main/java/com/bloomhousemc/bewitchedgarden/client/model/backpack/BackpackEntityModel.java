package com.bloomhousemc.bewitchedgarden.client.model.backpack;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;


@Environment(EnvType.CLIENT)
public class BackpackEntityModel<T extends LivingEntity & IAnimatable> extends AnimatedGeoModel<T> {

    @Override
    public Identifier getAnimationFileLocation(T obj) {
        return new Identifier(BewitchedGarden.MODID, "animations/backpack.animation.json");
    }

    @Override
    public Identifier getModelLocation(T obj) {
        return new Identifier(BewitchedGarden.MODID, "geo/backpack.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T obj) {
        return new Identifier(BewitchedGarden.MODID, "textures/block/backpack.png");
    }
}
