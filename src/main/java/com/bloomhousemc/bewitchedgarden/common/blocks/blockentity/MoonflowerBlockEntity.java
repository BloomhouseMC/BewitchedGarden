package com.bloomhousemc.bewitchedgarden.common.blocks.blockentity;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MoonflowerBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public MoonflowerBlockEntity(BlockPos pos, BlockState state) {
        super(BGObjects.MOONFLOWER_BLOCK_ENTITY, pos, state);
    }

    public String getMoon(){
        BewitchmentAPI.getMoonPhase(this.world);
        int moonPhase = BewitchmentAPI.getMoonPhase(this.world);
        return switch (moonPhase) {
            case 1 -> "waningGibbous";
            case 2 -> "thirdQuarter";
            case 3 -> "waningCrescent";
            case 4 -> "newMoon";
            case 5 -> "waxingCrescent";
            case 6 -> "firstQuarter";
            case 7 -> "waxingGibbous";
            default -> "fullMoon";
        };
    }


    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.moonflower."+getMoon(), true));
        return PlayState.CONTINUE;
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return super.writeNbt(nbt);
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
