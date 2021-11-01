package com.bloomhousemc.bewitchedgarden.common.registry;

import net.minecraft.item.FoodComponent;

public class BGFoodComponents {
    public static final FoodComponent TIER_1_FOOD = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).build();
    public static final FoodComponent TIER_2_FOOD = new FoodComponent.Builder().hunger(2).saturationModifier(0.6f).build();
    public static final FoodComponent TIER_3_FOOD = new FoodComponent.Builder().hunger(4).saturationModifier(1.5f).build();
    public static final FoodComponent TIER_4_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(2f).build();
    public static final FoodComponent TIER_5_FOOD = new FoodComponent.Builder().hunger(8).saturationModifier(2.5f).build();
    public static final FoodComponent TIER_6_FOOD = new FoodComponent.Builder().hunger(10).saturationModifier(3f).build();
    public static final FoodComponent TIER_7_FOOD = new FoodComponent.Builder().hunger(12).saturationModifier(4f).build();
}
