package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.CommandModule;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleFabric;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class FabricModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules();
        CommandRegistrationCallback.EVENT.register(CommandModule::register);
        ConfigModuleFabric.init();
    }
}
