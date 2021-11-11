package com.bloomhousemc.bewitchedgarden.mixin;

import com.bloomhousemc.bewitchedgarden.common.registry.BGTags;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@SuppressWarnings("ConstantConditions")
@Mixin(Entity.class)
public abstract class EntityMixin  {
    @Shadow
    public float fallDistance;

    @Shadow protected boolean submergedInWater;

    @Shadow @Nullable protected Tag<Fluid> submergedFluidTag;

    @Shadow public abstract boolean isSubmergedIn(Tag<Fluid> fluidTag);

    @Shadow public abstract void extinguish();

    @Shadow public abstract boolean isSubmergedInWater();

    @Shadow
    public abstract boolean isSwimming();

    @Shadow
    public abstract boolean isSprinting();


    @Shadow
    public abstract boolean hasVehicle();

    @Shadow
    public World world;

    @Shadow
    public abstract boolean updateMovementInFluid(Tag<Fluid> tag, double d);


    @Shadow public abstract void setSwimming(boolean swimming);

    @Shadow
    private BlockPos blockPos;

    @Shadow public abstract double getX();

    @Shadow public abstract double getZ();


    @Inject(method = "updateSubmergedInWaterState", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/Entity;isSubmergedIn(Lnet/minecraft/tag/Tag;)Z", shift = At.Shift.AFTER))
    private void updateFluid(CallbackInfo ci) {
        if(!this.submergedInWater){
            this.submergedInWater = this.isSubmergedIn(BGTags.POISON);
        }
    }

    @Inject(method = "checkWaterState()V", at = @At(value = "TAIL"))
    private void fluidPushing(CallbackInfo ci) {
        if (this.updateMovementInFluid(BGTags.POISON, 0.014D)) {
            this.fallDistance = 0.0F;
            this.extinguish();
        }
    }

    @Inject(method = "updateSubmergedInWaterState", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void markEyesInFluid2(CallbackInfo ci, double eyeHeight) {
        BlockPos blockPos = new BlockPos(this.getX(), eyeHeight, this.getZ());
        FluidState fluidState = this.world.getFluidState(blockPos);
        if (fluidState.isIn(BGTags.POISON)) {
            double fluidHeight = (float)blockPos.getY() + fluidState.getHeight(this.world, blockPos);
            if (fluidHeight > eyeHeight) {
                this.submergedFluidTag = BGTags.POISON;
                ci.cancel();
            }
        }
    }

    @Inject(method = "updateSwimming()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSprinting()Z", ordinal = 1, shift = At.Shift.AFTER))
    private void setSwimming(CallbackInfo ci) {
        if(!this.isSwimming() && this.isSprinting() && this.isSubmergedInWater() && !this.hasVehicle()){
            this.setSwimming(this.world.getFluidState(this.blockPos).isIn(BGTags.POISON));
        }
    }

}
