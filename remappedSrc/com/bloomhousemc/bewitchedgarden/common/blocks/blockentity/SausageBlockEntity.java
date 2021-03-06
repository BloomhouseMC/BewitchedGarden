package com.bloomhousemc.bewitchedgarden.common.blocks.blockentity;

import com.bloomhousemc.bewitchedgarden.common.blocks.SausageBlock;
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

public class SausageBlockEntity extends HangerHerbBlockEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public SausageBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        String string = switch (event.getAnimatable().getWorld().getBlockState(getPos()).get(SausageBlock.SAUSAGES)) {
            case 1 -> "one";
            case 2 -> "two";
            case 3 -> "three";
            case 4 -> "four";
            default -> "";
        };
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sausage."+string, true));
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
