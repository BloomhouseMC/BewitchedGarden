package com.bloomhousemc.bewitchedgarden.client.model.backpack;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.items.BackpackItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BackpackItemModel extends AnimatedGeoModel<BackpackItem> {
    @Override
    public Identifier getModelLocation(BackpackItem object) {
        return new Identifier(BewitchedGarden.MODID, "geo/backpack_item.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BackpackItem object) {
        return new Identifier(BewitchedGarden.MODID, "textures/block/backpack.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BackpackItem animatable) {
        return new Identifier(BewitchedGarden.MODID, "animations/backpack_item.animation.json");
    }
}