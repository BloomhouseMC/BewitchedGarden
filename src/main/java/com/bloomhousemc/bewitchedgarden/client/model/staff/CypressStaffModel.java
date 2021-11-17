package com.bloomhousemc.bewitchedgarden.client.model.staff;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.items.staff.CypressStaffItem;
import com.bloomhousemc.bewitchedgarden.common.items.staff.JuniperStaffItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CypressStaffModel extends AnimatedGeoModel<CypressStaffItem> {
    @Override
    public Identifier getModelLocation(CypressStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "geo/cypress_staff.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CypressStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "textures/item/staff/staffs.png");
    }

    @Override
    public Identifier getAnimationFileLocation(CypressStaffItem animatable) {
        return new Identifier(BewitchedGarden.MODID, "animations/cypress_staff.animation.json");
    }
}