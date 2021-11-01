package com.bloomhousemc.bewitchedgarden.client.model;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.entity.BasePlantEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BasePlantEntityModel extends AnimatedGeoModel<BasePlantEntity> {
    public String getEntity(BasePlantEntity plantEntity){
        return Registry.ENTITY_TYPE.getKey(plantEntity.getType()).get().getValue().getPath();
    }
    @Override
    public Identifier getAnimationFileLocation(BasePlantEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "animations/"+getEntity(entity)+".animation.json");
    }

    @Override
    public Identifier getModelLocation(BasePlantEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "geo/"+getEntity(entity)+".geo.json");
    }

    @Override
    public Identifier getTextureLocation(BasePlantEntity entity) {
        return new Identifier(BewitchedGarden.MODID, "textures/entity/plant/plants.png");
    }
}