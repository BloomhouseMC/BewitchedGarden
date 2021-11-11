package com.bloomhousemc.bewitchedgarden.mixin;

import com.bloomhousemc.bewitchedgarden.common.registry.BGTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {

    @Shadow protected boolean isSubmergedInWater;

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "updateWaterSubmersionState()Z",
    at = @At(value = "RETURN"), cancellable = true)
    private void poisonUnderwater(CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) {
            this.isSubmergedInWater = this.isSubmergedIn(BGTags.POISON);
            if(this.isSubmergedInWater) cir.setReturnValue(true);
        }
    }
}
