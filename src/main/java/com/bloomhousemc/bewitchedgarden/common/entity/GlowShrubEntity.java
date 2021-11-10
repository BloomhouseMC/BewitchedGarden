package com.bloomhousemc.bewitchedgarden.common.entity;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class GlowShrubEntity extends BasePlantEntity {
    public GlowShrubEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(this.getY());
        int k = MathHelper.floor(this.getZ());
        BlockPos blockPos = new BlockPos(i, j+1, k);
        if(world.getBlockState(blockPos).isAir()){
            BlockState blockState = BGObjects.WISP.getDefaultState();
            this.world.setBlockState(blockPos, blockState);
        }
        super.tick();
    }

    @Override
    protected void initGoals() {
    }
}
