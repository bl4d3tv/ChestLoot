package com.github.bl4d3tv.chestloot;

import com.github.bl4d3tv.chestloot.callbacks.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChestLoot implements ModInitializer {
    public static final String MOD_ID = "chestloot";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing ChestLoot");
        BlockPlaceCallback.EVENT.register(new BlockPlaceHandler());
        CommandRegistrationCallback.EVENT.register(new CommandRegistrationHandler());
        ServerLifecycleEvents.SERVER_STARTING.register(new ServerLifecycleHandler());
        UseBlockCallback.EVENT.register(new UseBlockHandler());
    }
}
