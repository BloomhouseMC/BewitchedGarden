package com.bloomhousemc.bewitchedgarden.client.renderer;

import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.WispBlockEntity;
import moriyashiine.bewitchment.common.registry.BWParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WispBlockEntityRenderer implements BlockEntityRenderer<WispBlockEntity> {
    int cooldown = 0;
    @Override
    public void render(WispBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        if (world != null) {
            BlockPos pos = entity.getPos();
            cooldown++;
            if(!MinecraftClient.getInstance().isPaused() && cooldown >= 40){
                cooldown = 0;
                world.addParticle(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                0, 0.05, 0);
            }
        }
    }
}
