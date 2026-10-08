# Framed biome thumbnail atlas for Minecraft 26.3 / Fabric

Traveler's Titles renders mini **20x20 pixel-art biome landscapes in a
Minecraft-style frame**, positioned before the translated biome title. The
vignettes are rendered in the HUD with `GuiGraphicsExtractor.blit` and
`RenderPipelines.GUI_TEXTURED`, not as font glyphs. Each scene uses its
original colours; biome text retains its per-biome palette and fading.

* Atlas: `assets/travelerstitles/textures/gui/biome_thumbnails.png`
  (160x180 pixels, eight columns, nine rows).
* Mapping: `BiomeThumbnails.java` (row-major, 67 vanilla biome IDs).
* 26.3: includes `dappled_forest` (autumn tree) and `sulfur_caves`
  (yellow crystals).
* Atlas tile 67: fallback for unknown / modded biomes.
* Existing `showBiomeIcons` user setting in Cloth Config controls thumbnails.
* A resource pack may replace the atlas at the same path.
* Biome names remain `Component.translatable("biome.minecraft.<id>")`;
  translation packs can supply localized and styled text.
* When a translation already includes a legacy Private-Use-Area pack glyph,
  no extra thumbnail is added to avoid duplicates.
* The images are original hand-authored pixel artwork; no third-party
  resource pack art is copied or redistributed.

Code backup: `backup/26.3-before-pixel-biome-thumbnails`.
