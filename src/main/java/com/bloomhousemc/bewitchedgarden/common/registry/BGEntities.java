package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.entity.*;
import com.bloomhousemc.bewitchedgarden.common.items.itementity.MutandisEntity;
import com.sun.jna.platform.win32.GL;
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
    public static final EntityType<MuncherEntity> MUNCHER_ENTITY = create("muncher", FabricEntityTypeBuilder.<MuncherEntity>create(SpawnGroup.CREATURE, MuncherEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build());
    public static final EntityType<GlowShrubEntity> GLOW_SHRUB_ENTITY = create("glowshrub", FabricEntityTypeBuilder.<GlowShrubEntity>create(SpawnGroup.CREATURE, GlowShrubEntity::new).dimensions(EntityDimensions.fixed(0.6F, 0.5F)).build());
    public static final EntityType<GrabberEntity> GRABBER_ENTITY = create("grabber", FabricEntityTypeBuilder.<GrabberEntity>create(SpawnGroup.CREATURE, GrabberEntity::new).dimensions(EntityDimensions.fixed(0.4F, 1.95F)).build());
    public static final EntityType<ElderEntity> ELDER_ENTITY = create("elder", FabricEntityTypeBuilder.<ElderEntity>create(SpawnGroup.CREATURE, ElderEntity::new).dimensions(EntityDimensions.fixed(3F, 3F)).build());
    public static final EntityType<LeafletEntity> LEAFLET = create("leaflet", FabricEntityTypeBuilder.<LeafletEntity>create(SpawnGroup.CREATURE, LeafletEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.6F)).build());
    public static final EntityType<CruptidEntity> CRUPTID = create("cruptid", FabricEntityTypeBuilder.<CruptidEntity>create(SpawnGroup.MONSTER, CruptidEntity::new).dimensions(EntityDimensions.fixed(0.7F, 0.6F)).build());
    public static final EntityType<EffigyEntity> EFFIGY = create("effigy", FabricEntityTypeBuilder.<EffigyEntity>create(SpawnGroup.MONSTER, EffigyEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.8F)).build());


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
        FabricDefaultAttributeRegistry.register(MUNCHER_ENTITY, BasePlantEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(GLOW_SHRUB_ENTITY, BasePlantEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(GRABBER_ENTITY, BasePlantEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ELDER_ENTITY, BasePlantEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(LEAFLET, LeafletEntity.createLeafletAttributes());
        FabricDefaultAttributeRegistry.register(CRUPTID, CruptidEntity.createCruptidAttributes());
        FabricDefaultAttributeRegistry.register(EFFIGY, EffigyEntity.createAttributes());
        /*
        for(EntityType entityType : WKEntities.ENTITY_TYPES.keySet()){
            FabricDefaultAttributeRegistry.register(entityType, BasePlantEntity.createAttributes());
         }

         */
    }
}
