package com.yungnickyoung.minecraft.travelerstitles.module;

import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.command.BiomeTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.DimensionTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.ReloadConfigCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public final class CommandModule {
    private CommandModule() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection environment) {
        BiomeTitleCommand.register(dispatcher, context, environment);
        DimensionTitleCommand.register(dispatcher, context, environment);
        ReloadConfigCommand.register(dispatcher, context, environment);
    }
}
