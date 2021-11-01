package com.bloomhousemc.bewitchedgarden.client.shader;

import com.bloomhousemc.bewitchedgarden.common.registry.BGStatusEffects;
import ladysnake.satin.api.event.PostWorldRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ToxicShader implements PostWorldRenderCallback, ServerTickEvents.StartWorldTick{
    public boolean renderingEffect = false;
    private float prog;
    private int ticks;
    public @Nullable PlayerEntity player = null;
    public ManagedShaderEffect shader = ShaderEffectManager.getInstance().manage(new Identifier("bewitchedgarden:shaders/post/toxic.json"), shader -> {
        MinecraftClient mc = MinecraftClient.getInstance();

    });
    private final Uniform1f uniformSTime = shader.findUniform1f("Time");
    private final Uniform1f uniformProg = shader.findUniform1f("Prog");

    public void init() {
        PostWorldRenderCallback.EVENT.register(this);
        ServerTickEvents.START_WORLD_TICK.register(this);
    }

    @Override
    public void onWorldRendered(Camera camera, float tickDelta, long nanoTime) {
        if(renderingEffect){
            this.uniformSTime.set((ticks + tickDelta) / 20f);
            prog++;
            shader.render(tickDelta);
            float progcalc = (float) Math.exp(-1 * ((prog/100)-10)*((prog/100)-10)/15);
            uniformProg.set(progcalc);
        }else{
            prog=0;
        }
    }

    @Override
    public void onStartTick(ServerWorld world) {
        world.getPlayers().stream().forEach(serverPlayerEntity -> {
            if(renderingEffect && !serverPlayerEntity.hasStatusEffect(BGStatusEffects.TOXIC)){
                renderingEffect = false;
            }
            if(serverPlayerEntity.hasStatusEffect(BGStatusEffects.TOXIC)){
                renderingEffect = true;
            }
        });
    }
}
