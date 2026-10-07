package com.yungnickyoung.minecraft.travelerstitles;

import net.fabricmc.api.ModInitializer;

public class TravelersTitlesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TravelersTitlesCommon.init();
    }
}
