package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.world.feature.BloodRootFeature;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class BGWorld {

    // Herbs
    public static final RandomPatchFeatureConfig MINT_CONFIG = new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(BGObjects.MINT.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS)).cannotProject().build();
    public static final ConfiguredFeature<?, ?> MINT = register("mint", Feature.RANDOM_PATCH.configure(MINT_CONFIG));

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
