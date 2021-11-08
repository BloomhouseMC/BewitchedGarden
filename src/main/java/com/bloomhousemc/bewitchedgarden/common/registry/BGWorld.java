package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class BGWorld {

    // Herbs
    public static final RandomPatchFeatureConfig MINT_CONFIG = new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(BGObjects.MINT.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS)).cannotProject().build();
    public static final ConfiguredFeature<?, ?> MINT = register("mint", Feature.RANDOM_PATCH.configure(MINT_CONFIG));

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(BewitchedGarden.MODID, id), feature);
    }

    public static void init() {

        // World Generation
        BiomeModification world = BiomeModifications.create(new Identifier(BewitchedGarden.MODID, "features"));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(Biome.Category.FOREST), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, MINT));
    }
}
