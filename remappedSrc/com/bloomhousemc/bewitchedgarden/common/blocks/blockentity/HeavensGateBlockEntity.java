package com.bloomhousemc.bewitchedgarden.common.blocks.blockentity;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;

import static com.bloomhousemc.bewitchedgarden.common.blocks.HeavensGateBlock.OPEN;

public class HeavensGateBlockEntity extends BlockEntity implements IAnimatable {
    public final List<Long> blockList = new ArrayList<>();
    public int counter = 0;
    public int MAX_COUNT = 100;
    private final AnimationFactory factory = new AnimationFactory(this);
    public HeavensGateBlockEntity(BlockPos pos, BlockState state) {
        super(BGObjects.HEAVENS_GATE_BLOCK_ENTITY, pos, state);
    }

    public int getCounter(){
        return this.counter;
    }





    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.getAnimatable().getWorld().getBlockState(pos).get(OPEN) && counter <= MAX_COUNT){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.door.opening", true));
            counter++;
        }
        if(event.getAnimatable().getWorld().getBlockState(pos).get(OPEN) && counter >= MAX_COUNT){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.door.open", true));
            counter=MAX_COUNT;
        }
        if(!event.getAnimatable().getWorld().getBlockState(pos).get(OPEN) && counter >= 0){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.door.closing", true));
            counter--;
        }
        if(!event.getAnimatable().getWorld().getBlockState(pos).get(OPEN) && counter <= 0){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.door.closed", true));
            counter=0;
        }

        //event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.door.closed", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }



}
