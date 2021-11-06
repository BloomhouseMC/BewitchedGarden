package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.client.renderer.*;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.LeechChestBlockEntity;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.client.render.TexturedRenderLayers.CHEST_ATLAS_TEXTURE;

public class BGRendering {

    // Sprites
    public static final SpriteIdentifier LEECH_CHEST_SPRITE = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchedGarden.MODID, "entity/chest/leech"));

    public static void init() {
        //Entities
        EntityRendererRegistry.INSTANCE.register(BGEntities.HERBOLOGIST, HerbologistEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.LEAFLET, LeafletEntityRenderer::new);

        //Plants
        EntityRendererRegistry.INSTANCE.register(BGEntities.JUPITER_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.VENUS_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.SATURN_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.ELDER_ENTITY, BasePlantEntityRenderer::new);

        //LayerMaps
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BGObjects.LUNARIAN, BGObjects.PEAS);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BGObjects.SNARE, BGObjects.FIERY_SNARE, BGObjects.NIGHT_SNARE, BGObjects.TELEPORTATION_SNARE, BGObjects.CAPTURE_SNARE);
        BlockRenderLayerMap.INSTANCE.putBlock(BGObjects.MINT, RenderLayer.getCutout());

        //BlockEntity
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.SAUSAGE_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new SausageBlockEntityRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.WISP_BLOCK_ENTITY, ctx -> new WispBlockEntityRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.HEAVENS_GATE_BLOCK_ENTITY, ctx -> new HeavensGateBlockEntityRenderer());
        //BlockEntityRendererRegistry.INSTANCE.register(BGObjects.HEAVENS_GATE_BLOCK_ENTITY, HeavensGateBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.LEECH_CHEST_BLOCK_ENTITY, LeechChestEntityRenderer::new);

        //ItemEntity
        EntityRendererRegistry.INSTANCE.register(BGEntities.MUTANDIS_ENTITY_ENTITY_TYPE, (context) -> new FlyingItemEntityRenderer(context));

        // Item
        BuiltinItemRendererRegistry.INSTANCE.register(BGObjects.LEECH_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(new LeechChestBlockEntity(BGObjects.LEECH_CHEST_BLOCK_ENTITY, BlockPos.ORIGIN, BGObjects.LEECH_CHEST.getDefaultState()), matrices, vertexConsumers, light, overlay));

        // Sprites
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(LEECH_CHEST_SPRITE);
        ClientSpriteRegistryCallback.event(CHEST_ATLAS_TEXTURE).register((texture, registry) -> {
           registry.register(new Identifier(BewitchedGarden.MODID, "entity/chest/leech"));
        });
    }
}
