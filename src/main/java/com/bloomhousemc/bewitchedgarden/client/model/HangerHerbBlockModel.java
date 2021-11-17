package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.GarlicHangerBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.HangerHerbBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.entity.BasePlantEntity;
import com.bloomhousemc.bewitchedgarden.common.items.staff.BaseStaffItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HangerHerbBlockModel extends AnimatedGeoModel<HangerHerbBlockEntity> {
    public String getEntity(HangerHerbBlockEntity hangerHerbBlockEntity){
        String string = hangerHerbBlockEntity.getCachedState().getBlock().getName().getString();
        return Registry.BLOCK.getKey(hangerHerbBlockEntity.getCachedState().getBlock()).get().getValue().getPath();
    }

    @Override
    public Identifier getAnimationFileLocation(HangerHerbBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/"+getEntity(entity)+".animation.json");
    }

    @Override
    public Identifier getModelLocation(HangerHerbBlockEntity animatable) {
        return new Identifier(BewitchedGarden.MODID, "geo/"+getEntity(animatable)+".geo.json");
    }

    @Override
    public Identifier getTextureLocation(HangerHerbBlockEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/block/"+getEntity(entity)+".png");
    }
}