package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BGSounds {

    public static SoundEvent MUSIC_DISC_PETALS = register("music_disc.petals");

    static SoundEvent register(String id) {
        SoundEvent sound = new SoundEvent(new Identifier(BewitchedGarden.MODID, id));
        Registry.register(Registry.SOUND_EVENT, new Identifier(BewitchedGarden.MODID, id), sound);
        return sound;
    }
}
