package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.blocks.*;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.*;
import com.bloomhousemc.bewitchedgarden.common.blocks.crops.LunarianCropBlock;
import com.bloomhousemc.bewitchedgarden.common.blocks.crops.PeasCropBlock;
import com.bloomhousemc.bewitchedgarden.common.blocks.snares.*;
import com.bloomhousemc.bewitchedgarden.common.items.*;
import com.bloomhousemc.bewitchedgarden.common.fluids.PoisonFluid;
import com.bloomhousemc.bewitchedgarden.common.items.staff.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import static com.bloomhousemc.bewitchedgarden.common.registry.BGFoodComponents.*;

import java.util.LinkedHashMap;
import java.util.Map;


public class BGObjects {
    public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    public static final Map<FlowableFluid, Identifier> FLUIDS = new LinkedHashMap<>();
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

    // Crops/Plants
    public static final Block LUNARIAN = register("lunarian", new LunarianCropBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().ticksRandomly().noCollision().sounds(BlockSoundGroup.CROP)), false);
    public static final Block MINT = register("mint", new HerbPlantBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().noCollision().sounds(BlockSoundGroup.GRASS)), true);
    public static final Block PEAS = register("peas", new PeasCropBlock(FabricBlockSettings.copyOf(LUNARIAN)), false);
    public static final Block BLOODROOT = register("bloodroot", new HerbPlantBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().noCollision().sounds(BlockSoundGroup.GRASS)), false);

    // Snares
        public static final Block SNARE = register("snare", new SnareBlock(FabricBlockSettings.of(Material.PLANT).noCollision().strength(0.3F).sounds(BlockSoundGroup.BAMBOO)), true);
        public static final Block TELEPORTATION_SNARE = register("teleportation_snare", new TeleportationSnareBlock(FabricBlockSettings.of(Material.PLANT).noCollision().strength(0.3F).sounds(BlockSoundGroup.BAMBOO)), true);
        public static final Block NIGHT_SNARE = register("night_snare", new NightSnareBlock(FabricBlockSettings.copyOf(SNARE)), true);
        public static final Block FIERY_SNARE = register("fiery_snare", new FierySnareBlock(FabricBlockSettings.copyOf(SNARE)), true);
        public static final Block CAPTURE_SNARE = register("capture_snare", new CaptureSnareBlock(FabricBlockSettings.copyOf(SNARE)), true);

    //Items
    public static final Item MUNCHER_POISON = register("muncher_poison", new Item(gen()));
    public static final Item GLOWSHRUB_BULB = register("glowshrub_bulb", new Item(gen()));
    public static final Item GRABBER_STEM = register("grabber_stem", new Item(gen()));
    public static final Item LUNARIAN_SEEDS = register("lunarian_seeds", new AliasedBlockItem(LUNARIAN, gen()));
    public static final Item LUNAR_PETAL = register("lunar_petal", new Item(gen()));
    public static final Item MINT_LEAVES = register("mint_leaves", new Item(gen()));
    public static final Item PEA_POD = register("pea_pod", new AliasedBlockItem(PEAS, gen()));

    public static final Block BACKPACK_BLOCK = register("backpack_block", new BackpackBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)), false);

    public static final Item BACKPACK_ITEM = register("backpack_item", new BackpackItem(BACKPACK_BLOCK, gen()));


    public static final Item BLOODROOT_ITEM = register("bloodroot_item", new Item(gen()));
    public static final Item MUTANDIS = register("mutandis", new MutandisItem(gen()));
    public static final Item MUTANDIS_BREW = register("mutandis_brew", new MutandisBrew(gen()));

    public static final Item MUSIC_DISC_PETALS = register("music_disc_petals", new BGMusicDisc(7, BGSounds.MUSIC_DISC_PETALS, gen().maxCount(1).rarity(Rarity.RARE)));

    // Staff Bases
    public static final Item DRAGONBLOOD_BASE_STAFF = register("dragonblood_base_staff", new BaseStaffItem(0, gen().maxCount(1).rarity(Rarity.RARE)));
    public static final Item ELDER_BASE_STAFF = register("elder_base_staff", new BaseStaffItem(0, gen().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item JUNIPER_BASE_STAFF = register("juniper_base_staff", new BaseStaffItem(0, gen().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item CYPRESS_BASE_STAFF = register("cypress_base_staff", new BaseStaffItem(0, gen().maxCount(1).rarity(Rarity.UNCOMMON)));

    // Staffs
    public static final Item MOSS_STAFF = register("moss_staff", new MossStaffItem(100, gen().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item ENCHANTED_STAFF = register("enchanted_staff", new EnchantedStaffItem(200, gen().maxCount(1).rarity(Rarity.RARE)));
    public static final Item PURITY_STAFF = register("purity_staff", new PurityStaffItem(300, gen().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item JUNIPER_STAFF = register("juniper_staff", new JuniperStaffItem(300, gen().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item CYPRESS_STAFF = register("cypress_staff", new CypressStaffItem(300, gen().maxCount(1).rarity(Rarity.UNCOMMON)));

    //Fluids
    public static final FlowableFluid POISON_FLUID_STILL = register("poison_fluid_still", new PoisonFluid.Still());
    public static final FlowableFluid POISON_FLUID_FLOWING = register("poison_fluid_flowing", new PoisonFluid.Flowing());
    public static final Item POISON_BUCKET = register("poison_bucket", new BucketItem(POISON_FLUID_STILL, gen().recipeRemainder(Items.BUCKET).maxCount(1)));

    //Blocks

    public static final Block CORRUPTED_GRASS = register("corrupted_grass", new CorruptedGrass(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)), true);
    public static final Block CORRUPTED_DIRT = register("corrupted_dirt", new CorruptedDirt(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)), true);
    public static final Block WISP = register("wisp", new WispBlock(FabricBlockSettings.copyOf(Blocks.STONE)), false);
    public static final Block LEECH_CHEST = register("leech_chest", new LeechChestBlock(FabricBlockSettings.of(Material.PLANT).strength(2.5F).sounds(BlockSoundGroup.MOSS_BLOCK)), true);
    public static final Block HEAVENS_GATE = register("heavens_gate", new HeavensGateBlock(FabricBlockSettings.copyOf(Blocks.STONE)), true);
    public static final Block EMPTY_HEAVENS_GATE = register("empty_heavens_gate", new EmptyHeavensGateBlock(FabricBlockSettings.copyOf(Blocks.STONE)), false);
    public static final Block POISON = register("poison_fluid_block", new PoisonBlock(POISON_FLUID_STILL, FabricBlockSettings.copy(Blocks.WATER)), false);
    public static final Block POISON_PUDDLE = register("poison_puddle", new PoisonBlock.PoisonPuddle(POISON_FLUID_STILL, FabricBlockSettings.of(Material.WATER)), false);

    public static final Block GARLIC_HANGER = register("garlic_hanger", new HangerHerbBlock(FabricBlockSettings.copyOf(Blocks.STONE)), false);
    public static final Block SAUSAGE_HANGER = register("sausage_hanger", new SausageBlock(FabricBlockSettings.copyOf(Blocks.STONE)), false);
    public static final Block ROWAN_HANGER = register("rowan_hanger", new HangerHerbBlock(FabricBlockSettings.copyOf(Blocks.STONE)), false);

    // BlockItems
    public static final Item SAUSAGE_HANGER_ITEM = register("sausage_hanger_item", new BlockItem(SAUSAGE_HANGER, gen().food(TIER_5_FOOD)));
    public static final Item GARLIC_HANGER_ITEM = register("garlic_hanger_item", new BlockItem(GARLIC_HANGER, gen().food(TIER_1_FOOD)));
    public static final Item ROWAN_HANGER_ITEM = register("rowan_hanger_item", new BlockItem(ROWAN_HANGER, gen()));

    //Block Entities

    public static final BlockEntityType<WispBlockEntity> WISP_BLOCK_ENTITY = register("wisp_block_entity", FabricBlockEntityTypeBuilder.create(WispBlockEntity::new, WISP).build(null));
    public static final BlockEntityType<LeechChestBlockEntity> LEECH_CHEST_BLOCK_ENTITY = register("leech_chest_block_entity", FabricBlockEntityTypeBuilder.create(LeechChestBlockEntity::new, LEECH_CHEST).build(null));
    public static final BlockEntityType<HeavensGateBlockEntity> HEAVENS_GATE_BLOCK_ENTITY = register("heavens_gate_block_entity", FabricBlockEntityTypeBuilder.create(HeavensGateBlockEntity::new, HEAVENS_GATE).build(null));

    public static final BlockEntityType<SausageBlockEntity> SAUSAGE_BLOCK_ENTITY = register("sausage_block_entity", FabricBlockEntityTypeBuilder.create(SausageBlockEntity::new, SAUSAGE_HANGER).build(null));
    public static final BlockEntityType<HangerHerbBlockEntity> HANGER_HERB_BLOCK_ENTITY = register("hanger_herb", FabricBlockEntityTypeBuilder.create(HangerHerbBlockEntity::new, ROWAN_HANGER, GARLIC_HANGER, SAUSAGE_HANGER).build(null));

    public static final BlockEntityType<BackpackBlockEntity> BACKPACK_BLOCK_ENTITY = register("backpack_entity", FabricBlockEntityTypeBuilder.create(BackpackBlockEntity::new, BACKPACK_BLOCK).build(null));

    //Oils
    public static final Item MOONLIGHT_INFUSION = register("moonlight_infusion", new Item(gen().recipeRemainder(Items.GLASS_BOTTLE)));
    public static final Item ENDER_VIAL = register("ender_vial", new Item(gen().recipeRemainder(Items.GLASS_BOTTLE)));//0x70922d

    //Spawn Eggs
    public static final Item MUNCHER_SPAWN_EGG = register("muncher_spawn_egg", new SpawnEggItem(BGEntities.MUNCHER_ENTITY, 0x70922d, 0x44101c, gen()));
    public static final Item GLOW_SHRUB_SPAWN_EGG = register("glowshrub_spawn_egg", new SpawnEggItem(BGEntities.GLOW_SHRUB_ENTITY, 0x70922d, 0x7FFAED, gen()));
    public static final Item GRABBER_SPAWN_EGG = register("grabber_spawn_egg", new SpawnEggItem(BGEntities.GRABBER_ENTITY, 0x70922d, 0x5C1148, gen()));
    public static final Item ELDER_SPAWN_EGG = register("elder_spawn_egg", new SpawnEggItem(BGEntities.ELDER_ENTITY, 0x70922d, 0x382E12, gen()));


    private static <T extends FlowableFluid> T register(String id, T fluid){
        FLUIDS.put(fluid, new Identifier(BewitchedGarden.MODID, id));
        return fluid;
    }

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
        FLUIDS.keySet().forEach(fluid -> Registry.register(Registry.FLUID, FLUIDS.get(fluid), fluid));
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
