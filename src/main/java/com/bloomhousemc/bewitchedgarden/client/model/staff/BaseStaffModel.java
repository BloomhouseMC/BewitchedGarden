package com.bloomhousemc.bewitchedgarden.client.model.staff;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.items.staff.BaseStaffItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BaseStaffModel extends AnimatedGeoModel<BaseStaffItem> {
    public String getWood(BaseStaffItem item){
        return Registry.ITEM.getKey(item).get().getValue().getPath();
    }

    @Override
    public Identifier getModelLocation(BaseStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "geo/"+getWood(object)+".geo.json");
    }

    @Override
    public Identifier getTextureLocation(BaseStaffItem object) {
        return new Identifier(BewitchedGarden.MODID, "textures/item/staff/staffs.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BaseStaffItem animatable) {
        return new Identifier(BewitchedGarden.MODID, "animations/base_staff.animation.json");
    }
}