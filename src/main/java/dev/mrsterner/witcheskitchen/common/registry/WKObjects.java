package dev.mrsterner.witcheskitchen.common.registry;

import dev.mrsterner.witcheskitchen.WitchesKitchen;
import dev.mrsterner.witcheskitchen.common.blocks.SausageBlock;
import dev.mrsterner.witcheskitchen.common.blocks.blockentity.SausageBlockEntity;
import dev.mrsterner.witcheskitchen.common.items.SausageItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import static dev.mrsterner.witcheskitchen.common.registry.WKFoodComponents.*;

import java.util.LinkedHashMap;
import java.util.Map;


public class WKObjects {
    public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();


    //Items
    public static final Item VENUS_POISON = register("venus_poison", new Item(gen()));
    public static final Item JUPITER_BULB = register("jupiter_bulb", new Item(gen()));
    public static final Item SATURN_STEM = register("saturn_stem", new Item(gen()));

    public static final Item SAUSAGE_ITEM = register("sausage_item", new SausageItem(gen().food(TIER_5_FOOD)));

    //Blocks
    public static final Block SAUSAGE = register("sausage", new SausageBlock(FabricBlockSettings.copyOf(Blocks.STONE)), false);

    //Block Entities
    public static final BlockEntityType<SausageBlockEntity> SAUSAGE_BLOCK_ENTITY = register("sausage_block_entity", FabricBlockEntityTypeBuilder.create(SausageBlockEntity::new, SAUSAGE).build(null));





    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(WitchesKitchen.MODID, id));
        return type;
    }

    private static <T extends Block> T register(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(WitchesKitchen.MODID, name));
        if (createItem) {
            ITEMS.put(new BlockItem(block, gen()), BLOCKS.get(block));
        }
        return block;
    }


    private static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, new Identifier(WitchesKitchen.MODID, name));
        return item;
    }

    private static Item.Settings gen() {
        return new Item.Settings().group(WitchesKitchen.WITCHESKITCHEN_GROUP);
    }

    public static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
