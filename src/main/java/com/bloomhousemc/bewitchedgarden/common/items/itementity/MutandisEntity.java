package com.bloomhousemc.bewitchedgarden.common.items.itementity;

import com.bloomhousemc.bewitchedgarden.common.items.MutandisBrew;
import com.bloomhousemc.bewitchedgarden.common.registry.BGEntities;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.bloomhousemc.bewitchedgarden.common.registry.BGTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ObserverBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MutandisEntity extends ThrownItemEntity implements FlyingItemEntity {
    public int count = 0;
    public static final BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");

    public MutandisEntity(World world, LivingEntity owner) {
        super(BGEntities.MUTANDIS_ENTITY_ENTITY_TYPE, owner, world);
    }

    public MutandisEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Item getDefaultItem() {
        return BGObjects.MUTANDIS_BREW;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.DRAGON_BREATH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }
    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        System.out.println("onBlockHit");
        PlayerEntity playerEntity = (PlayerEntity) getOwner();
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
        //areaEffectCloudEntity.setWaitTime(0);
        areaEffectCloudEntity.setRadius(2.5F);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setParticleType(ParticleTypes.DRAGON_BREATH);
        areaEffectCloudEntity.setDuration(10);
        areaEffectCloudEntity.setColor(1);
        this.world.spawnEntity(areaEffectCloudEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockPos origo = blockPos.add(-1,1,-1);
        //List<BlockState> list = new ArrayList<>();
        List<BlockPos> listPos = new ArrayList<>();
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                //list.add(i+j,this.world.getBlockState(origo.add(i,0,j)));
                listPos.add(i+j,origo.add(i,0,j));
            }
        }
        listPos.forEach(blockPos1 -> {
            if(BGTags.MUTANDIS.contains(this.world.getBlockState(blockPos1).getBlock())){
                BlockState mutating = BGTags.MUTANDIS.getRandom(this.world.random).getDefaultState();
                if(mutating.getBlock() instanceof Waterloggable){
                    this.world.setBlockState(blockPos1, mutating.with(WATERLOGGED, false));
                }else{
                    this.world.setBlockState(blockPos1,mutating);
                }
            }
        });
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) { // checks if the world is client
            this.world.sendEntityStatus(this, (byte)3); // particle?
            this.kill(); // kills the projectile
        }
    }
}
