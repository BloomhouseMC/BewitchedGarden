package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.client.renderer.*;
import com.bloomhousemc.bewitchedgarden.client.renderer.backpack.BackpackEntityRenderer;
import com.bloomhousemc.bewitchedgarden.client.renderer.backpack.BackpackItemRenderer;
import com.bloomhousemc.bewitchedgarden.client.renderer.staff.*;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.LeechChestBlockEntity;
import com.bloomhousemc.bewitchedgarden.client.renderer.backpack.BackpackRenderer;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import java.util.function.Function;

import static net.minecraft.client.render.TexturedRenderLayers.CHEST_ATLAS_TEXTURE;

public class BGRendering {

    // Sprites
    public static final SpriteIdentifier LEECH_CHEST_SPRITE = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchedGarden.MODID, "entity/chest/leech"));

    public static void init() {
        //Entities
        EntityRendererRegistry.INSTANCE.register(BGEntities.HERBOLOGIST, HerbologistEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.LEAFLET, LeafletEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.CRUPTID, CruptidEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.EFFIGY, EffigyEntityRenderer::new);

        //PlantEntities
        EntityRendererRegistry.INSTANCE.register(BGEntities.MUNCHER_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.GLOW_SHRUB_ENTITY, BasePlantEntityRenderer::new);//TODO remove glowshrub
        EntityRendererRegistry.INSTANCE.register(BGEntities.GRABBER_ENTITY, BasePlantEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(BGEntities.ELDER_ENTITY, ElderEntityRenderer::new);


        EntityRendererRegistry.INSTANCE.register(BGEntities.BACKPACK_ENTITY, BackpackEntityRenderer::new);

        //LayerMaps
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BGObjects.LUNARIAN, BGObjects.PEAS, BGObjects.MINT, BGObjects.BLOODROOT);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BGObjects.SNARE, BGObjects.FIERY_SNARE, BGObjects.NIGHT_SNARE, BGObjects.TELEPORTATION_SNARE, BGObjects.CAPTURE_SNARE);

        //BlockEntity
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.SAUSAGE_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new HangerHerbBlockEntityRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.WISP_BLOCK_ENTITY, ctx -> new WispBlockEntityRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.HEAVENS_GATE_BLOCK_ENTITY, ctx -> new HeavensGateBlockEntityRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.MOONFLOWER_BLOCK_ENTITY, ctx -> new MoonflowerBlockEntityRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.LEECH_CHEST_BLOCK_ENTITY, LeechChestEntityRenderer::new);


        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.HANGER_HERB_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new HangerHerbBlockEntityRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BGObjects.BACKPACK_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new BackpackRenderer());


        //ItemEntity
        EntityRendererRegistry.INSTANCE.register(BGEntities.MUTANDIS_ENTITY_ENTITY_TYPE, (context) -> new FlyingItemEntityRenderer(context));

        // Item
        BuiltinItemRendererRegistry.INSTANCE.register(BGObjects.LEECH_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(new LeechChestBlockEntity(BGObjects.LEECH_CHEST_BLOCK_ENTITY, BlockPos.ORIGIN, BGObjects.LEECH_CHEST.getDefaultState()), matrices, vertexConsumers, light, overlay));
        GeoItemRenderer.registerItemRenderer(BGObjects.ENCHANTED_STAFF, new EnchantedStaffRenderer());
        GeoItemRenderer.registerItemRenderer(BGObjects.PURITY_STAFF, new PurityStaffRenderer());
        GeoItemRenderer.registerItemRenderer(BGObjects.JUNIPER_STAFF, new JuniperStaffRenderer());
        GeoItemRenderer.registerItemRenderer(BGObjects.CYPRESS_STAFF, new CypressStaffRenderer());

        GeoItemRenderer.registerItemRenderer(BGObjects.DRAGONBLOOD_BASE_STAFF, new BaseStaffRenderer());
        GeoItemRenderer.registerItemRenderer(BGObjects.ELDER_BASE_STAFF, new BaseStaffRenderer());
        GeoItemRenderer.registerItemRenderer(BGObjects.JUNIPER_BASE_STAFF, new BaseStaffRenderer());
        GeoItemRenderer.registerItemRenderer(BGObjects.CYPRESS_BASE_STAFF, new BaseStaffRenderer());

        GeoItemRenderer.registerItemRenderer(BGObjects.BACKPACK_ITEM, new BackpackItemRenderer());
        // Sprites
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(LEECH_CHEST_SPRITE);
        ClientSpriteRegistryCallback.event(CHEST_ATLAS_TEXTURE).register((texture, registry) -> {
           registry.register(new Identifier(BewitchedGarden.MODID, "entity/chest/leech"));
        });

        // ColorProvider
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->
        view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) :
        FoliageColors.getDefaultColor(), BGObjects.BLOODROOT);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x8700DB, BGObjects.POISON);

        // Fluid shenanigans
        setupFluidRendering(BGObjects.POISON_FLUID_STILL, BGObjects.POISON_FLUID_FLOWING, new Identifier(BewitchedGarden.MODID, "poison_fluid"), 0x8700DB);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), BGObjects.POISON_FLUID_STILL, BGObjects.POISON_FLUID_FLOWING);
    }



    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_still");
        final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_flow");

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }
            @Override
            public void reload(ResourceManager resourceManager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }
        });

        final FluidRenderHandler renderHandler = new FluidRenderHandler()
        {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
    }
}
