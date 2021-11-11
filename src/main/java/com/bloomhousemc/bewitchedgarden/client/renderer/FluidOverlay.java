package com.bloomhousemc.bewitchedgarden.client.renderer;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import com.bloomhousemc.bewitchedgarden.common.registry.BGTags;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FluidOverlay {
    private static final Identifier POISON_TEXTURE_UNDERWATER = new Identifier(BewitchedGarden.MODID + ":textures/misc/poison_fluid_underwater.png");

    public static boolean renderPoinsonOverlay(ClientPlayerEntity clientPlayerEntity, MatrixStack matrixStack) {
        if(!clientPlayerEntity.isSubmergedIn(BGTags.POISON)) {
            return false;
        }

        BlockState state = clientPlayerEntity.world.getBlockState(new BlockPos(clientPlayerEntity.getEyePos()));
        if (state.isOf(BGObjects.POISON)) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableTexture();
            RenderSystem.setShaderTexture(0, POISON_TEXTURE_UNDERWATER);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            float brightness = (float) Math.max(
            Math.pow(FluidOverlay.getDimensionBrightnessAtEyes(clientPlayerEntity), 2D),
            clientPlayerEntity.world.getDimension().getBrightness(0));
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(brightness, brightness, brightness, 0.95F);
            float modifiedYaw = -clientPlayerEntity.getYaw() / (64.0F * 8F);
            float modifiedPitch = clientPlayerEntity.getPitch() / (64.0F * 8F);
            Matrix4f matrix4f = matrixStack.peek().getModel();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).texture(1.0F + modifiedYaw, 1.0F + modifiedPitch).next();
            bufferBuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).texture(0.0F + modifiedYaw, 2.0F + modifiedPitch).next();
            bufferBuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).texture(1.0F + modifiedYaw, 1.0F + modifiedPitch).next();
            bufferBuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).texture(2.0F + modifiedYaw, 0.0F + modifiedPitch).next();
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
            RenderSystem.disableBlend();
            return true;
        }

        return false;
    }

    public static void renderPoisonFog(Camera camera) {
        FluidState fluidstate = getNearbyPoisonFluid(camera);
        if(fluidstate.isIn(BGTags.POISON)) {
            RenderSystem.setShaderFogStart(0.35f);
            RenderSystem.setShaderFogEnd(4);
        }
    }

    public static float getDimensionBrightnessAtEyes(Entity entity){
        float lightLevelAtEyes = entity.world.getBrightness(new BlockPos(entity.getEyePos()));
        return lightLevelAtEyes / 15f;
    }

    public static FluidState getNearbyPoisonFluid(Camera camera){
        Entity entity = camera.getFocusedEntity();
        World world = entity.world;
        FluidState fluidstate = world.getFluidState(camera.getBlockPos());

        Vec3d currentPos = camera.getPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        double offsetDistanceCheck = 0.075D;

        for(Direction direction : Direction.values()) {
            double x = currentPos.x + direction.getOffsetX() * offsetDistanceCheck;
            double y = currentPos.y + direction.getOffsetY() * offsetDistanceCheck;
            double z = currentPos.z + direction.getOffsetZ() * offsetDistanceCheck;
            mutable.set(x, y, z);
            if(!mutable.equals(camera.getBlockPos())) {
                FluidState neighboringFluidstate = world.getFluidState(mutable);
                if(neighboringFluidstate.isIn(BGTags.POISON)) {
                    fluidstate = neighboringFluidstate;
                }
            }
        }
        return fluidstate;
    }

}
