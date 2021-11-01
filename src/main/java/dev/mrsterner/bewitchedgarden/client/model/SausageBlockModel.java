package dev.mrsterner.bewitchedgarden.client.model;

import dev.mrsterner.bewitchedgarden.BewitchedGarden;
import dev.mrsterner.bewitchedgarden.common.blocks.blockentity.SausageBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SausageBlockModel extends AnimatedGeoModel<SausageBlockEntity> {

    @Override
    public Identifier getAnimationFileLocation(SausageBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/sausage.animation.json");
    }

    @Override
    public Identifier getModelLocation(SausageBlockEntity animatable) {
        return new Identifier(BewitchedGarden.MODID, "geo/sausage.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SausageBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/block/sausage.png");
    }
}