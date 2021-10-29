package dev.mrsterner.witcheskitchen.common.registry;

import dev.mrsterner.witcheskitchen.WitchesKitchen;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class WKTags {
    public static final Tag<Item> CUREABLE = TagRegistry.item(new Identifier(WitchesKitchen.MODID, "cureable"));
}
