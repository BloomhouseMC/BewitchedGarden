package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.items.staff.EnchantedStaffItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EnchantedStaffModel extends AnimatedGeoModel<EnchantedStaffItem> {
    @Override
    public Identifier getModelLocation(EnchantedStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "geo/enchanted_staff.geo.json");
    }

    @Override
    public Identifier getTextureLocation(EnchantedStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "textures/item/staff/enchanted_staff.png");
    }

    @Override
    public Identifier getAnimationFileLocation(EnchantedStaffItem animatable) {
        return new Identifier(BewitchedGarden.MODID, "animations/enchanted_staff.animation.json");
    }
}