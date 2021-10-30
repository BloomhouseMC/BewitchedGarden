package dev.mrsterner.witcheskitchen.client.model;

import dev.mrsterner.witcheskitchen.WitchesKitchen;
import dev.mrsterner.witcheskitchen.common.entity.VenusEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class VenusEntityModel extends AnimatedGeoModel<VenusEntity> {
    @Override
    public Identifier getAnimationFileLocation(VenusEntity entity) {
        return new Identifier(WitchesKitchen.MODID, "animations/venus.animation.json");
    }

    @Override
    public Identifier getModelLocation(VenusEntity entity) {
        return new Identifier(WitchesKitchen.MODID, "geo/venus.geo.json");
    }

    @Override
    public Identifier getTextureLocation(VenusEntity entity) {
        return new Identifier(WitchesKitchen.MODID, "textures/entity/venus.png");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(VenusEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("base1");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            //head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}