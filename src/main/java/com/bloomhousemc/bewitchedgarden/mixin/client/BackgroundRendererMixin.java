package com.bloomhousemc.bewitchedgarden.mixin.client;

import com.bloomhousemc.bewitchedgarden.client.renderer.FluidOverlay;
import com.bloomhousemc.bewitchedgarden.common.registry.BGTags;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Shadow
    private static float red;
    @Shadow
    private static float green;
    @Shadow
    private static float blue;
    @Shadow
    private static long lastWaterFogColorUpdateTime = -1L;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld$Properties;getHorizonShadingRatio()D"))
    private static void method(Camera camera, float tickDelta, ClientWorld world, int i, float f, CallbackInfo ci){
        FluidState fluidstate = FluidOverlay.getNearbyPoisonFluid(camera);
        if(fluidstate.isIn(BGTags.POISON)) {
            red = 0.3F ;
            green = 0.0F ;
            blue = 0.8F;
            lastWaterFogColorUpdateTime = -1L;
        }
    }
    @Inject(method = "applyFog", at=@At(value = "INVOKE", target = "com/mojang/blaze3d/systems/RenderSystem.setShaderFogEnd(F)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void renderPoisonFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        FluidOverlay.renderPoisonFog(camera);
    }
}
