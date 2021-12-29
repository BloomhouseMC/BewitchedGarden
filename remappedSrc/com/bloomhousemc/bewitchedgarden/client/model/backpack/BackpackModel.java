package com.bloomhousemc.bewitchedgarden.client.model.backpack;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.BackpackBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BackpackModel extends AnimatedGeoModel<BackpackBlockEntity> {
    @Override
    public Identifier getModelLocation(BackpackBlockEntity object) {
        return new Identifier(BewitchedGarden.MODID, "geo/backpack_item.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BackpackBlockEntity object) {
        return new Identifier(BewitchedGarden.MODID, "textures/block/backpack.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BackpackBlockEntity animatable) {
        return new Identifier(BewitchedGarden.MODID, "animations/backpack_item.animation.json");
    }
}