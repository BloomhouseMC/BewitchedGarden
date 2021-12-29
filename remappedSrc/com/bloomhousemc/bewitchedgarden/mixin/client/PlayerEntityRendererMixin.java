package com.bloomhousemc.bewitchedgarden.mixin.client;

import com.bloomhousemc.bewitchedgarden.common.entity.player.BackpackEntity;
import com.bloomhousemc.bewitchedgarden.common.registry.BGEntities;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo callbackInfo) {
        BackpackEntity entity = BGEntities.BACKPACK_ENTITY.create(player.world);
        if (entity != null && player.getEquippedStack(EquipmentSlot.CHEST).isOf(BGObjects.BACKPACK_ITEM)) {
            entity.age = player.age;
            entity.bodyYaw = player.bodyYaw;
            entity.prevBodyYaw = player.prevBodyYaw;
            entity.setPitch(player.getPitch());
            entity.prevPitch = player.prevPitch;
            entity.setSneaking(player.isSneaking());
            entity.setPose(player.getPose());
            if (player.hasVehicle()) {
                entity.startRiding(player.getVehicle(), true);
            }
            float width = 1 / (entity.getType().getWidth() / EntityType.PLAYER.getWidth());
            matrixStack.scale(width, 1 / (entity.getType().getHeight() / EntityType.PLAYER.getHeight()), width);
            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
        }
    }
}