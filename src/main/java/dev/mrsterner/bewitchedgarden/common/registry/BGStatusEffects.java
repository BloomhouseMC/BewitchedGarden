package dev.mrsterner.bewitchedgarden.common.registry;

import dev.mrsterner.bewitchedgarden.BewitchedGarden;
import dev.mrsterner.bewitchedgarden.common.statuseffects.ToxicStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class BGStatusEffects {
    private static final Map<StatusEffect, Identifier> STATUS_EFFECTS = new LinkedHashMap<>();

    public static final StatusEffect TOXIC = create("toxic", new ToxicStatusEffect(StatusEffectCategory.HARMFUL, 0x555D50));


    private static <T extends StatusEffect> T create(String name, T effect) {
        STATUS_EFFECTS.put(effect, new Identifier(BewitchedGarden.MODID, name));
        return effect;
    }

    public static void init() {
        STATUS_EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, STATUS_EFFECTS.get(effect), effect));
    }
}
