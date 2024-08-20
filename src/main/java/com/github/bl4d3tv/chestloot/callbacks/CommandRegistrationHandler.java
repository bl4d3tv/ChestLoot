package com.github.bl4d3tv.chestloot.callbacks;

import com.github.bl4d3tv.chestloot.ChestLoot;
import com.github.bl4d3tv.chestloot.ChestLootConfig;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class CommandRegistrationHandler implements CommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("chestloot").then(CommandManager.literal("reload").executes(context -> {
            ChestLoot.LOGGER.info("Reloading chestloot.json");

            ChestLootConfig.load();

            context.getSource().sendFeedback(Text.literal("Reloaded chestloot.json"), false);
            return 1;
        })));
    }
}
