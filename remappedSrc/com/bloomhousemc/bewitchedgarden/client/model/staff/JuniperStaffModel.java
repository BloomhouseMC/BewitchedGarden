package com.bloomhousemc.bewitchedgarden.client.model.staff;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.items.staff.JuniperStaffItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JuniperStaffModel extends AnimatedGeoModel<JuniperStaffItem> {
    @Override
    public Identifier getModelLocation(JuniperStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "geo/juniper_staff.geo.json");
    }

    @Override
    public Identifier getTextureLocation(JuniperStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "textures/item/staff/staffs.png");
    }

    @Override
    public Identifier getAnimationFileLocation(JuniperStaffItem animatable) {
        return new Identifier(BewitchedGarden.MODID, "animations/juniper_staff.animation.json");
    }
}