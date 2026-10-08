# Biome icons for Minecraft 26.3

Traveler's Titles embeds **original**, tiny glyphs for 67 vanilla biome IDs including
`minecraft:dappled_forest` and `minecraft:sulfur_caves`, plus one generic icon for
third-party / future biome IDs. Each glyph lives in its own U+E100…U+E143 slot
of `travelerstitles:biome_icons`.

- Font: `assets/travelerstitles/font/biome_icons.json`
- Texture: `assets/travelerstitles/textures/font/biome_icons.png`
- Mapping: `BiomeIcons.java` (ordered, 8 glyphs per atlas row).
- User toggle: `biomes.showBiomeIcons` (default `true`).
- Palette: separate `travelerstitles.biome.minecraft.<id>.color` language keys.
- Text: stays `Component.translatable("biome.minecraft.<id>")`, keeping localization.
- If another pack includes an existing Private-Use-Area icon in that translation,
  Traveler's Titles deliberately does not add a second icon.
- A custom resource pack can **replace** these glyphs by overriding the font JSON
  or the texture, without any modification to the title code.

The prior Icons pack included biome glyphs until v1.11.3; these were removed in
v1.11.4. **Do not copy or redistribute Icons' assets**: its terms prohibit
redistribution of modified or unmodified textures without permission.
This project ships independent, procedurally drawn monochrome art, not copies.

Rollback branch: `backup/26.3-before-biome-icons` (commit
`3b3b641dc5ebf2e08011a1f803b787260280d4a4`).
