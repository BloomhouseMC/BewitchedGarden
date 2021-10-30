package dev.mrsterner.witcheskitchen.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;

public class VenusEntity extends BasePlantEntity implements IAnimatable {
    public VenusEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }
}
