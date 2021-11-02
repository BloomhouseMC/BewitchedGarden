package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.blocks.*;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.WispBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.blocks.snares.FierySnareBlock;
import com.bloomhousemc.bewitchedgarden.common.blocks.snares.NightSnareBlock;
import com.bloomhousemc.bewitchedgarden.common.blocks.snares.SnareBlock;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.SausageBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.blocks.snares.TeleportationSnareBlock;
import com.bloomhousemc.bewitchedgarden.common.items.MutandisItem;
import com.bloomhousemc.bewitchedgarden.common.items.SausageItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import static com.bloomhousemc.bewitchedgarden.common.registry.BGFoodComponents.*;

import java.util.LinkedHashMap;
import java.util.Map;


public class BGObjects {
    public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

    // Crops/Plants
    public static final Block LUNARIAN = register("lunarian", new LunarianCropBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().ticksRandomly().noCollision().sounds(BlockSoundGroup.CROP)), false);
    public static final Block MINT = register("mint", new HerbPlantBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().noCollision().sounds(BlockSoundGroup.GRASS)), true);
        // Snares
        public static final Block SNARE = register("snare", new SnareBlock(FabricBlockSettings.of(Material.PLANT).noCollision().strength(0.3F).sounds(BlockSoundGroup.BAMBOO)), true);
        public static final Block TELEPORTATION_SNARE = register("teleportation_snare", new TeleportationSnareBlock(FabricBlockSettings.of(Material.PLANT).noCollision().strength(0.3F).sounds(BlockSoundGroup.BAMBOO)), true);
        public static final Block NIGHT_SNARE = register("night_snare", new NightSnareBlock(FabricBlockSettings.copyOf(SNARE)), true);
        public static final Block FIERY_SNARE = register("fiery_snare", new FierySnareBlock(FabricBlockSettings.copyOf(SNARE)), true);

    //Items
    public static final Item VENUS_POISON = register("venus_poison", new Item(gen()));
    public static final Item JUPITER_BULB = register("jupiter_bulb", new Item(gen()));
    public static final Item SATURN_STEM = register("saturn_stem", new Item(gen()));
    public static final Item LUNARIAN_SEEDS = register("lunarian_seeds", new AliasedBlockItem(LUNARIAN, gen()));
    public static final Item LUNAR_PETAL = register("lunar_petal", new Item(gen()));
    public static final Item MINT_LEAVES = register("mint_leaves", new Item(gen()));

    public static final Item SAUSAGE_ITEM = register("sausage_item", new SausageItem(gen().food(TIER_5_FOOD)));
    public static final Item BLOODROOT = register("bloodroot", new Item(gen()));
    public static final Item MUTANDIS = register("mutandis", new MutandisItem(gen()));

    //Blocks
    public static final Block SAUSAGE = register("sausage", new SausageBlock(FabricBlockSettings.copyOf(Blocks.STONE)), false);
    public static final Block CORRUPTED_GRASS = register("corrupted_grass", new CorruptedGrass(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)), true);
    public static final Block CORRUPTED_DIRT = register("corrupted_dirt", new CorruptedDirt(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)), true);
    public static final Block WISP = register("wisp", new WispBlock(FabricBlockSettings.copyOf(Blocks.STONE)), false);

    //Block Entities
    public static final BlockEntityType<SausageBlockEntity> SAUSAGE_BLOCK_ENTITY = register("sausage_block_entity", FabricBlockEntityTypeBuilder.create(SausageBlockEntity::new, SAUSAGE).build(null));
    public static final BlockEntityType<WispBlockEntity> WISP_BLOCK_ENTITY = register("wisp_block_entity", FabricBlockEntityTypeBuilder.create(WispBlockEntity::new, WISP).build(null));

    //Oils
    public static final Item MOONLIGHT_INFUSION = register("moonlight_infusion", new Item(gen().recipeRemainder(Items.GLASS_BOTTLE)));
    public static final Item ENDER_VIAL = register("ender_vial", new Item(gen().recipeRemainder(Items.GLASS_BOTTLE)));//0x70922d

    //Spawn Eggs
    public static final Item VENUS_SPAWN_EGG = register("venus_spawn_egg", new SpawnEggItem(BGEntities.VENUS_ENTITY, 0x70922d, 0x44101c, gen()));
    public static final Item JUPITER_SPAWN_EGG = register("jupiter_spawn_egg", new SpawnEggItem(BGEntities.JUPITER_ENTITY, 0x70922d, 0x7FFAED, gen()));
    public static final Item SATURN_SPAWN_EGG = register("saturn_spawn_egg", new SpawnEggItem(BGEntities.SATURN_ENTITY, 0x70922d, 0x5C1148, gen()));
    public static final Item ELDER_SPAWN_EGG = register("elder_spawn_egg", new SpawnEggItem(BGEntities.ELDER_ENTITY, 0x70922d, 0x382E12, gen()));



    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(BewitchedGarden.MODID, id));
        return type;
    }

    private static <T extends Block> T register(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(BewitchedGarden.MODID, name));
        if (createItem) {
            ITEMS.put(new BlockItem(block, gen()), BLOCKS.get(block));
        }
        return block;
    }


    private static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, new Identifier(BewitchedGarden.MODID, name));
        return item;
    }

    private static Item.Settings gen() {
        return new Item.Settings().group(BewitchedGarden.BEWITCHEDGARDEN_GROUP);
    }

    public static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
