package dev.mrsterner.witcheskitchen.common.registry;

import dev.mrsterner.witcheskitchen.WitchesKitchen;
import dev.mrsterner.witcheskitchen.common.entity.BasePlantEntity;
import dev.mrsterner.witcheskitchen.common.entity.HerbologistEntity;
import dev.mrsterner.witcheskitchen.common.entity.JupiterEntity;
import dev.mrsterner.witcheskitchen.common.entity.VenusEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class WKEntities {
    public static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    public static final EntityType<HerbologistEntity> HERBOLOGIST = create("herbologist", FabricEntityTypeBuilder.<HerbologistEntity>create(SpawnGroup.CREATURE, HerbologistEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).trackRangeBlocks(10).build());
    public static final EntityType<VenusEntity> VENUS_ENTITY = create("venus", FabricEntityTypeBuilder.<VenusEntity>create(SpawnGroup.CREATURE, VenusEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build());
    public static final EntityType<JupiterEntity> JUPITER_ENTITY = create("jupiter", FabricEntityTypeBuilder.<JupiterEntity>create(SpawnGroup.CREATURE, JupiterEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build());

    private static <T extends Entity> EntityType<T> create(String name, EntityType<T> type) {
        ENTITY_TYPES.put(type, new Identifier(WitchesKitchen.MODID, name));
        return type;
    }

    private static void registerEntitySpawn(EntityType<?> type, Predicate<BiomeSelectionContext> predicate, int weight, int minGroupSize, int maxGroupSize) {
        BiomeModifications.addSpawn(predicate, type.getSpawnGroup(), type, weight, minGroupSize, maxGroupSize);
    }

    public static void init() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));

        FabricDefaultAttributeRegistry.register(HERBOLOGIST, HerbologistEntity.createHerbologistAttributes());

        for(EntityType entityType : WKEntities.ENTITY_TYPES.keySet()){
            FabricDefaultAttributeRegistry.register(entityType, BasePlantEntity.createAttributes());
         }
    }
}
