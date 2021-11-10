package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.world.feature.BloodRootFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;

public class BGWorld {

    // Herbs
    public static final ConfiguredFeature<?, ?> MINT = register("mint", Feature.RANDOM_PATCH.configure(ConfiguredFeatures.Configs.GRASS_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(5));

    private static final Feature<DefaultFeatureConfig> BLOODROOT_GEN = new BloodRootFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> BLOODROOT_GEN_CONFIGURED = BLOODROOT_GEN.configure(FeatureConfig.DEFAULT)
    .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(20)));


    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(BewitchedGarden.MODID, id), feature);
    }

    public static void init() {
        Registry.register(Registry.FEATURE, new Identifier(BewitchedGarden.MODID, "bloodroot_gen"), BLOODROOT_GEN);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(BewitchedGarden.MODID, "bloodroot_gen"), BLOODROOT_GEN_CONFIGURED);
    }
}
