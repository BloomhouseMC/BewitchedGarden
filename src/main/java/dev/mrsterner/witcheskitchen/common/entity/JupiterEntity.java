package dev.mrsterner.witcheskitchen.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;

public class JupiterEntity extends BasePlantEntity implements IAnimatable {
    public JupiterEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(this.getY());
        int k = MathHelper.floor(this.getZ());
        BlockPos blockPos = new BlockPos(i, j+2, k);
        if(world.getBlockState(blockPos).isAir()){
            BlockState blockState = Blocks.LIGHT.getDefaultState();
            this.world.setBlockState(blockPos, blockState);
        }
        super.tick();
    }
}
