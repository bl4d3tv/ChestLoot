package com.github.bl4d3tv.chestloot.callbacks;

import com.github.bl4d3tv.chestloot.ChestLootConfig;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class ServerLifecycleHandler implements ServerLifecycleEvents.ServerStarting {
    @Override
    public void onServerStarting(MinecraftServer server) {
        ChestLootConfig.load();
    }
}
