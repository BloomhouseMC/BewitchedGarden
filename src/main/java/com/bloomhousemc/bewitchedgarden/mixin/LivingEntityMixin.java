package com.bloomhousemc.bewitchedgarden.mixin;

import com.bloomhousemc.bewitchedgarden.common.fluids.PoisonFluid;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.bloomhousemc.bewitchedgarden.common.registry.BGTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract boolean shouldSwimInFluids();

    @Shadow
    public abstract Vec3d method_26317(double d, boolean bl, Vec3d vec3d);

    @Shadow
    public abstract boolean canWalkOnFluid(Fluid fluid);

    @Shadow
    protected boolean jumping;


    @Inject(method = "canMoveVoluntarily", at = @At("HEAD"), cancellable = true)
    private void snareTrap(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.getBlockStateAtPos().isOf(BGObjects.CAPTURE_SNARE)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "baseTick()V", at = @At(value = "TAIL"))
    private void breathing(CallbackInfo ci) {
        PoisonFluid.breathing((LivingEntity)(Object)this);
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getVelocity()Lnet/minecraft/util/math/Vec3d;"), cancellable = true)
    private void method(Vec3d movementInput,CallbackInfo ci){
        FluidState fluidState = this.world.getFluidState(this.getBlockPos());
        if (BGTags.POISON.contains(fluidState.getFluid()) && !this.canWalkOnFluid(fluidState.getFluid())) {
            double e = this.getY();
            double d = 0.08D;
            boolean bl = this.getVelocity().y <= 0.0D;
            this.updateVelocity(0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            Vec3d vec3d4;
            if (this.getFluidHeight(BGTags.POISON) <= this.getSwimHeight()) {
                this.setVelocity(this.getVelocity().multiply(0.5D, 0.800000011920929D, 0.5D));
                vec3d4 = this.method_26317(d, bl, this.getVelocity());
                this.setVelocity(vec3d4);
            } else {
                this.setVelocity(this.getVelocity().multiply(0.5D));
            }

            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0D, -d / 4.0D, 0.0D));
            }

            vec3d4 = this.getVelocity();
            if (this.horizontalCollision && this.doesNotCollide(vec3d4.x, vec3d4.y + 0.6000000238418579D - this.getY() + e, vec3d4.z)) {
                this.setVelocity(vec3d4.x, 0.30000001192092896D, vec3d4.z);
            }
            ci.cancel();
        }
    }


    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void method2(CallbackInfo ci){
        if (this.jumping && this.shouldSwimInFluids() && this.isInPoison()) {
            this.setVelocity(new Vec3d(this.getVelocity().x,this.getVelocity().y+0.005,this.getVelocity().z));
        }
    }

    @Unique
    public boolean isInPoison() {
        return !this.firstUpdate && this.fluidHeight.getDouble(BGTags.POISON) > 0.0D;
    }
}
