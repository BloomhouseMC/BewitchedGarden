package com.bloomhousemc.bewitchedgarden.mixin;

import com.bloomhousemc.bewitchedgarden.client.renderer.FluidOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {

    @Inject(method = "renderOverlays",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameOverlayRenderer;renderUnderwaterOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V"),
    locals = LocalCapture.CAPTURE_FAILSOFT,
    cancellable = true)
    private static void renderPoisonOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if(FluidOverlay.renderPoinsonOverlay(client.player, matrices)) {
            ci.cancel();
        }
    }
}
