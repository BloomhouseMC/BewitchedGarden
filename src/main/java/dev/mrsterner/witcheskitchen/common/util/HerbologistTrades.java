package dev.mrsterner.witcheskitchen.common.util;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.Random;

public class HerbologistTrades {
    public static final Int2ObjectMap<TradeOffers.Factory[]> HERBOLOGIST_TRADES;

    public HerbologistTrades() {
    }

    private static Int2ObjectMap<TradeOffers.Factory[]> copyToFastUtilMap(ImmutableMap<Integer, TradeOffers.Factory[]> map) {
        return new Int2ObjectOpenHashMap(map);
    }

    static {
        HERBOLOGIST_TRADES = copyToFastUtilMap(ImmutableMap.of(1, new TradeOffers.Factory[]{new HerbologistTrades.SellItemFactory(Items.SEA_PICKLE, 2, 1, 5, 1), new HerbologistTrades.SellItemFactory(Items.SLIME_BALL, 4, 1, 5, 1), new HerbologistTrades.SellItemFactory(Items.GLOWSTONE, 2, 1, 5, 1), new HerbologistTrades.SellItemFactory(Items.NAUTILUS_SHELL, 5, 1, 5, 1), new HerbologistTrades.SellItemFactory(Items.FERN, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.SUGAR_CANE, 1, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.PUMPKIN, 1, 1, 4, 1), new HerbologistTrades.SellItemFactory(Items.KELP, 3, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.CACTUS, 3, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.DANDELION, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.POPPY, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.BLUE_ORCHID, 1, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.ALLIUM, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.AZURE_BLUET, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.RED_TULIP, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.ORANGE_TULIP, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.WHITE_TULIP, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.PINK_TULIP, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.OXEYE_DAISY, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.CORNFLOWER, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1), new HerbologistTrades.SellItemFactory(Items.WHEAT_SEEDS, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.BEETROOT_SEEDS, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.PUMPKIN_SEEDS, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.MELON_SEEDS, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.ACACIA_SAPLING, 5, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.BIRCH_SAPLING, 5, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.DARK_OAK_SAPLING, 5, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.JUNGLE_SAPLING, 5, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.OAK_SAPLING, 5, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.SPRUCE_SAPLING, 5, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.RED_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.WHITE_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.BLUE_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.PINK_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.BLACK_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.GREEN_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.MAGENTA_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.YELLOW_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.GRAY_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.PURPLE_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.LIME_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.ORANGE_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.BROWN_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.CYAN_DYE, 1, 3, 12, 1), new HerbologistTrades.SellItemFactory(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.VINE, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.BROWN_MUSHROOM, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.RED_MUSHROOM, 1, 1, 12, 1), new HerbologistTrades.SellItemFactory(Items.LILY_PAD, 1, 2, 5, 1), new HerbologistTrades.SellItemFactory(Items.SMALL_DRIPLEAF, 1, 2, 5, 1), new HerbologistTrades.SellItemFactory(Items.SAND, 1, 8, 8, 1), new HerbologistTrades.SellItemFactory(Items.RED_SAND, 1, 4, 6, 1), new HerbologistTrades.SellItemFactory(Items.POINTED_DRIPSTONE, 1, 2, 5, 1), new HerbologistTrades.SellItemFactory(Items.ROOTED_DIRT, 1, 2, 5, 1), new HerbologistTrades.SellItemFactory(Items.MOSS_BLOCK, 1, 2, 5, 1)}, 2, new TradeOffers.Factory[]{new HerbologistTrades.SellItemFactory(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new HerbologistTrades.SellItemFactory(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new HerbologistTrades.SellItemFactory(Items.PACKED_ICE, 3, 1, 6, 1), new HerbologistTrades.SellItemFactory(Items.BLUE_ICE, 6, 1, 6, 1), new HerbologistTrades.SellItemFactory(Items.GUNPOWDER, 1, 1, 8, 1), new HerbologistTrades.SellItemFactory(Items.PODZOL, 3, 3, 6, 1)}));
    }

    static class SellItemFactory implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
            this(new ItemStack(item), price, count, maxUses, experience);
        }

        public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience) {
            this(stack, price, count, maxUses, experience, 0.05F);
        }

        public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        }
    }
}
