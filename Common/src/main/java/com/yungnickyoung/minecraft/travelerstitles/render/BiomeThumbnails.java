package com.yungnickyoung.minecraft.travelerstitles.render;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import java.util.List;

/**
 * Mapping for original framed pixel-art biome thumbnails in HUD atlas.
 * Ordered row-major, eight 20x20 tiles per row. No Icons pack textures bundled.
 */
public final class BiomeThumbnails {
    public static final int TILE_SIZE = 20;
    public static final int ATLAS_COLUMNS = 8;
    public static final int ATLAS_ROWS = 9;
    public static final Identifier ATLAS = TravelersTitlesCommon.id("textures/gui/biome_thumbnails.png");
    private static final List<String> BIOME_IDS = List.of(
        "badlands", "bamboo_jungle", "basalt_deltas", "beach",
        "birch_forest", "cherry_grove", "cold_ocean", "crimson_forest",
        "dappled_forest", "dark_forest", "deep_cold_ocean", "deep_dark",
        "deep_frozen_ocean", "deep_lukewarm_ocean", "deep_ocean", "desert",
        "dripstone_caves", "end_barrens", "end_highlands", "end_midlands",
        "eroded_badlands", "flower_forest", "forest", "frozen_ocean",
        "frozen_peaks", "frozen_river", "grove", "ice_spikes",
        "jagged_peaks", "jungle", "lukewarm_ocean", "lush_caves",
        "mangrove_swamp", "meadow", "mushroom_fields", "nether_wastes",
        "ocean", "old_growth_birch_forest", "old_growth_pine_taiga", "old_growth_spruce_taiga",
        "pale_garden", "plains", "river", "savanna",
        "savanna_plateau", "small_end_islands", "snowy_beach", "snowy_plains",
        "snowy_slopes", "snowy_taiga", "soul_sand_valley", "sparse_jungle",
        "stony_peaks", "stony_shore", "sulfur_caves", "sunflower_plains",
        "swamp", "taiga", "the_end", "the_void",
        "warm_ocean", "warped_forest", "windswept_forest", "windswept_gravelly_hills",
        "windswept_hills", "windswept_savanna", "wooded_badlands"
    
    );

    private BiomeThumbnails() {}

    public static int indexFor(Identifier biomeId, Component biomeTitle) {
        if (!TravelersTitlesCommon.CONFIG.biomes.showBiomeIcons || containsExistingPackGlyph(biomeTitle)) {
            return -1;
        }
        int idx = "minecraft".equals(biomeId.getNamespace())
            ? BIOME_IDS.indexOf(biomeId.getPath())
            : -1;
        // Atlas tile 67 is the generic image for modded / future biomes.
        return idx >= 0 ? idx : BIOME_IDS.size();
    }

    private static boolean containsExistingPackGlyph(Component component) {
        return component.getString().codePoints().anyMatch(cp ->
            Character.getType(cp) == Character.PRIVATE_USE
        );
    }
}
