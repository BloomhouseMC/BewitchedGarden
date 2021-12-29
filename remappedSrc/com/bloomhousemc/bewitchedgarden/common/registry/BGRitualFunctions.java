package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.ritualfunction.SummonLeafletRitualFunction;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.registry.BWRegistries;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class BGRitualFunctions {
    private static final Map<RitualFunction, Identifier> RITUAL_FUNCTIONS = new LinkedHashMap<>();

    public static final RitualFunction SUMMON_LEAFLET = create("summon_leaflet", new SummonLeafletRitualFunction(ParticleTypes.TOTEM_OF_UNDYING, null));

    private static <T extends RitualFunction> T create(String name, T ritualFunction) {
        RITUAL_FUNCTIONS.put(ritualFunction, new Identifier(BewitchedGarden.MODID, name));
        return ritualFunction;
    }

    public static void init() {
        RITUAL_FUNCTIONS.keySet().forEach(contract -> Registry.register(BWRegistries.RITUAL_FUNCTIONS, RITUAL_FUNCTIONS.get(contract), contract));
    }
}
