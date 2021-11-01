package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BGTags {
    public static final Tag<Item> CUREABLE = TagRegistry.item(new Identifier(BewitchedGarden.MODID, "cureable"));
}
