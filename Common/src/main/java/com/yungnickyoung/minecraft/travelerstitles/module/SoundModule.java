package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class SoundModule {
    public static final SoundEvent BIOME = register("biome");
    public static final SoundEvent DIMENSION = register("dimension");
    public static final SoundEvent WAYSTONE = register("waystone");

    private SoundModule() {
    }

    public static void init() {
        // Calling this method forces class initialization and registration.
    }

    private static SoundEvent register(String path) {
        Identifier id = TravelersTitlesCommon.id(path);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}
