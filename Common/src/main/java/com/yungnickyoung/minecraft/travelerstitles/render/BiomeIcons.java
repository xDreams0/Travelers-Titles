package com.yungnickyoung.minecraft.travelerstitles.render;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;

import java.util.List;

/**
 * Original biome glyphs, independently replaceable by resource packs.
 * No images or glyphs from the third-party Icons resource pack are bundled.
 */
public final class BiomeIcons {
    private static final int FIRST_CODEPOINT = 0xE100;
    private static final FontDescription.Resource ICON_FONT = new FontDescription.Resource(
        Identifier.fromNamespaceAndPath(TravelersTitlesCommon.MOD_ID, "biome_icons")
    );

    // Keep in the same order as bitmap glyphs, left to right then top to bottom.
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

    private BiomeIcons() {}

    public static Component withIcon(Identifier biomeId, Component originalTitle) {
        if (!TravelersTitlesCommon.CONFIG.biomes.showBiomeIcons || hasExistingPackIcon(originalTitle)) {
            return originalTitle;
        }

        // Use the final bitmap glyph for modded or future unknown biome IDs.
        int index = "minecraft".equals(biomeId.getNamespace()) ? BIOME_IDS.indexOf(biomeId.getPath()) : -1;
        if (index < 0) {
            index = BIOME_IDS.size();
        }

        Component icon = Component.literal(String.valueOf((char) (FIRST_CODEPOINT + index)))
            .withStyle(style -> style.withFont(ICON_FONT));
        return Component.empty()
            .append(icon)
            .append(Component.literal(" "))
            .append(originalTitle);
    }

    private static boolean hasExistingPackIcon(Component originalTitle) {
        // Avoid double icons with legacy packs that prefix PUA glyphs.
        return originalTitle.getString().codePoints().anyMatch(codepoint ->
            Character.getType(codepoint) == Character.PRIVATE_USE
        );
    }
}
