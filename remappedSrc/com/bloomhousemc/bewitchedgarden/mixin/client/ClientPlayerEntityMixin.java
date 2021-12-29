package com.bloomhousemc.bewitchedgarden.mixin.client;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    protected void snareTrapPlayer(CallbackInfo ci) {
        if (this.getBlockStateAtPos().isOf(BGObjects.CAPTURE_SNARE)) {
            ci.cancel();
        }
    }
}
