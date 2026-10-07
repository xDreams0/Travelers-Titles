package com.yungnickyoung.minecraft.travelerstitles;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.resources.Identifier;

public class TravelersTitlesFabric implements ModInitializer {
    private static final Identifier TITLES_HUD_ELEMENT =
        Identifier.fromNamespaceAndPath(TravelersTitlesCommon.MOD_ID, "titles");

    @Override
    public void onInitialize() {
        TravelersTitlesCommon.init();

        HudElementRegistry.addLast(
            TITLES_HUD_ELEMENT,
            (guiGraphics, deltaTracker) -> TravelersTitlesCommon.titleManager.renderTitles(
                guiGraphics,
                deltaTracker.getGameTimeDeltaPartialTick(false)
            )
        );
    }
}
