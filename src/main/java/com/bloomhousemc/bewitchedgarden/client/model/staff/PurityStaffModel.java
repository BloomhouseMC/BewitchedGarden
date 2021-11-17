package com.bloomhousemc.bewitchedgarden.client.model.staff;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.items.staff.EnchantedStaffItem;
import com.bloomhousemc.bewitchedgarden.common.items.staff.PurityStaffItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PurityStaffModel extends AnimatedGeoModel<PurityStaffItem> {
    @Override
    public Identifier getModelLocation(PurityStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "geo/purity_staff.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PurityStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "textures/item/staff/staffs.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PurityStaffItem animatable) {
        return new Identifier(BewitchedGarden.MODID, "animations/purity_staff.animation.json");
    }
}