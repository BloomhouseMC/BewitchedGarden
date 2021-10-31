package dev.mrsterner.witcheskitchen.client.model;

import dev.mrsterner.witcheskitchen.WitchesKitchen;
import dev.mrsterner.witcheskitchen.common.entity.BasePlantEntity;
import dev.mrsterner.witcheskitchen.common.entity.VenusEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BasePlantEntityModel extends AnimatedGeoModel<BasePlantEntity> {
    public String getEntity(BasePlantEntity plantEntity){
        return Registry.ENTITY_TYPE.getKey(plantEntity.getType()).get().getValue().getPath();
    }
    @Override
    public Identifier getAnimationFileLocation(BasePlantEntity entity) {
        return new Identifier(WitchesKitchen.MODID, "animations/"+getEntity(entity)+".animation.json");
    }

    @Override
    public Identifier getModelLocation(BasePlantEntity entity) {
        return new Identifier(WitchesKitchen.MODID, "geo/"+getEntity(entity)+".geo.json");
    }

    @Override
    public Identifier getTextureLocation(BasePlantEntity entity) {
        return new Identifier(WitchesKitchen.MODID, "textures/entity/plant/plants.png");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(BasePlantEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("base1");
        //System.out.println(head);
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            //head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            //head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}