package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.entity.*;
import com.bloomhousemc.bewitchedgarden.common.items.itementity.MutandisEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BGEntities {
    public static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    public static final EntityType<HerbologistEntity> HERBOLOGIST = create("herbologist", FabricEntityTypeBuilder.<HerbologistEntity>create(SpawnGroup.CREATURE, HerbologistEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).trackRangeBlocks(10).build());
    public static final EntityType<VenusEntity> VENUS_ENTITY = create("venus", FabricEntityTypeBuilder.<VenusEntity>create(SpawnGroup.CREATURE, VenusEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build());
    public static final EntityType<JupiterEntity> JUPITER_ENTITY = create("jupiter", FabricEntityTypeBuilder.<JupiterEntity>create(SpawnGroup.CREATURE, JupiterEntity::new).dimensions(EntityDimensions.fixed(0.6F, 0.5F)).build());
    public static final EntityType<SaturnEntity> SATURN_ENTITY = create("saturn", FabricEntityTypeBuilder.<SaturnEntity>create(SpawnGroup.CREATURE, SaturnEntity::new).dimensions(EntityDimensions.fixed(0.4F, 1.95F)).build());
    public static final EntityType<ElderEntity> ELDER_ENTITY = create("elder", FabricEntityTypeBuilder.<ElderEntity>create(SpawnGroup.CREATURE, ElderEntity::new).dimensions(EntityDimensions.fixed(3F, 3F)).build());

    public static final EntityType<MutandisEntity> MUTANDIS_ENTITY_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE, new Identifier(BewitchedGarden.MODID, "mutanis_entity"),
    FabricEntityTypeBuilder.<MutandisEntity>create(SpawnGroup.MISC, MutandisEntity::new)
    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build()
    );

    private static <T extends Entity> EntityType<T> create(String name, EntityType<T> type) {
        ENTITY_TYPES.put(type, new Identifier(BewitchedGarden.MODID, name));
        return type;
    }

    private static void registerEntitySpawn(EntityType<?> type, Predicate<BiomeSelectionContext> predicate, int weight, int minGroupSize, int maxGroupSize) {
        BiomeModifications.addSpawn(predicate, type.getSpawnGroup(), type, weight, minGroupSize, maxGroupSize);
    }

    public static void init() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));

        FabricDefaultAttributeRegistry.register(HERBOLOGIST, HerbologistEntity.createHerbologistAttributes());
        FabricDefaultAttributeRegistry.register(VENUS_ENTITY, BasePlantEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(JUPITER_ENTITY, BasePlantEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SATURN_ENTITY, BasePlantEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ELDER_ENTITY, BasePlantEntity.createAttributes());
        /*
        for(EntityType entityType : WKEntities.ENTITY_TYPES.keySet()){
            FabricDefaultAttributeRegistry.register(entityType, BasePlantEntity.createAttributes());
         }

         */
    }
}
