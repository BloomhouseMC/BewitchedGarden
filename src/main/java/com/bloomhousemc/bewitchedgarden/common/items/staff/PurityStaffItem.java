package com.bloomhousemc.bewitchedgarden.common.items.staff;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;

import java.util.Iterator;

public class PurityStaffItem extends BaseStaffItem implements IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);
    public PurityStaffItem(int maxStorage, Settings settings) {
        super(maxStorage, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (this.getStoredPower() >= 30) {
            if (!world.isClient) {
                // Not the most efficient way but it works
                Iterator var = BlockPos.Mutable.iterateOutwards(user.getBlockPos(), 10, 10, 10).iterator();
                while (var.hasNext()) {
                    BlockPos pos = (BlockPos)var.next();
                    if (world.getBlockState(pos).isOf(BGObjects.CORRUPTED_GRASS) || world.getBlockState(pos).isOf(BGObjects.CORRUPTED_DIRT)) {
                        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                        ParticleUtil.spawnParticle(Direction.Axis.Y, world, pos, 0.125D, ParticleTypes.ELECTRIC_SPARK, UniformIntProvider.create(1, 2));
                    }
                }
                this.remove(30);
                this.checkForOverfill();
            }
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.pass(stack);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.purity_staff.idle", true));
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
