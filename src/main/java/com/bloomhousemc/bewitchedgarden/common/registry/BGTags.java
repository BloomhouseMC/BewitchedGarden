package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BGTags {
    public static final Tag<Block> MUTANDIS = TagRegistry.block(new Identifier(BewitchedGarden.MODID, "mutandis"));
    public static final Tag<Fluid> POISON = TagRegistry.fluid(new Identifier(BewitchedGarden.MODID, "poison"));
}
