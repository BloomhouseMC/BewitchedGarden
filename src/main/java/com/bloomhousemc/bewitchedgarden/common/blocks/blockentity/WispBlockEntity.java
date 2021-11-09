package com.bloomhousemc.bewitchedgarden.common.blocks.blockentity;

import com.bloomhousemc.bewitchedgarden.common.entity.GlowShrubEntity;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;


public class WispBlockEntity extends BlockEntity {
    public int color = 0xffd450;
    public int lifetime = 40;
    public WispBlockEntity(BlockPos pos, BlockState state) {
        super(BGObjects.WISP_BLOCK_ENTITY, pos, state);
    }
    public static void tick(World world, BlockPos pos, BlockState state, WispBlockEntity blockEntity) {
        blockEntity.lifetime--;
        Box box = new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1);
        for (GlowShrubEntity it : world.getEntitiesByClass(GlowShrubEntity.class, box.expand(0, -3, 0), EntityPredicates.VALID_ENTITY)) {
            blockEntity.lifetime = 40;
        }
        if(blockEntity.lifetime < 0){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }
}
