package dev.mrsterner.witcheskitchen.client.model;

import dev.mrsterner.witcheskitchen.WitchesKitchen;
import dev.mrsterner.witcheskitchen.common.blocks.blockentity.SausageBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SausageBlockModel extends AnimatedGeoModel<SausageBlockEntity> {
    @Override
    public Identifier getAnimationFileLocation(SausageBlockEntity entity) {
        return new Identifier(WitchesKitchen.MODID, "animations/sausage.animation.json");
    }

    @Override
    public Identifier getModelLocation(SausageBlockEntity animatable) {
        return new Identifier(WitchesKitchen.MODID, "geo/sausage.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SausageBlockEntity entity) {
        return new Identifier(WitchesKitchen.MODID, "textures/block/sausage.png");
    }
}