package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.world.feature.BloodRootFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class BGWorld {
    public static final ConfiguredFeature<RandomPatchFeatureConfig, ?> PATCH_BLOOD_ROOT = ConfiguredFeatures.register("patch_blood_root_conf", Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(BGObjects.BLOODROOT))))));
    // Herbs
    //public static final RandomPatchFeatureConfig MINT_CONFIG = new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(BGObjects.MINT.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS)).cannotProject().build();
    //public static final ConfiguredFeature<?, ?> MINT = register("mint", Feature.RANDOM_PATCH.configure(MINT_CONFIG));

    private static final Feature<DefaultFeatureConfig> BLOODROOT_GEN = new BloodRootFeature(DefaultFeatureConfig.CODEC);
    //public static final ConfiguredFeature<?, ?> BLOODROOT_GEN_CONFIGURED = BLOODROOT_GEN.configure(FeatureConfig.DEFAULT)
    //.decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(20)));

    public static final PlacedFeature BLOOD_ROOT = PlacedFeatures.register("patch_blood_root", PATCH_BLOOD_ROOT.withPlacement(RarityFilterPlacementModifier.of(6),
    SquarePlacementModifier.of(),
    PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
//PATCH_CACTUS_DESERT = PlacedFeatures.register("patch_cactus_desert", VegetationConfiguredFeatures.PATCH_CACTUS.withPlacement(new PlacementModifier[]{RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()}));

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(BewitchedGarden.MODID, id), feature);
    }

    public static void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(BewitchedGarden.MODID, "patch_blood_root"), PATCH_BLOOD_ROOT);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(BewitchedGarden.MODID, "patch_blood_root"), BLOOD_ROOT);
    }
}
