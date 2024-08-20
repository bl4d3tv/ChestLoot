package com.github.bl4d3tv.chestloot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChestLootConfig {
    public static final Codec<Map<String, List<Identifier>>> CODEC = Codec.unboundedMap(Codec.STRING, Codec.list(Identifier.CODEC));

    private static final Map<String, List<Identifier>> lootTables = new HashMap<>();

    public static void load() {
        File file = FabricLoader.getInstance().getConfigDir().resolve("chestloot.json").toFile();

        // Load chestloot.json
        if (file.exists()) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(file)) {
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                lootTables.clear();
                CODEC.parse(JsonOps.INSTANCE, json).resultOrPartial(ChestLoot.LOGGER::error).ifPresent(lootTables::putAll);
                ChestLoot.LOGGER.info("Loaded chestloot.json");
            } catch (IOException e) {
                ChestLoot.LOGGER.error("Failed to load chestloot.json", e);
            }
        } else {
            // Write default chestloot.json
            try (InputStream is = ChestLootConfig.class.getResourceAsStream("/config.json")) {
                if (is == null) {
                    ChestLoot.LOGGER.error("Failed to load default chestloot.json resource");
                    return;
                }

                Files.copy(is, file.toPath());
            } catch (IOException e) {
                ChestLoot.LOGGER.error("Failed to write default chestloot.json", e);
            }
        }
    }

    public static @Nullable List<Identifier> getLootForBiome(@NotNull String id) {
        return lootTables.getOrDefault(id, lootTables.get("default"));
    }
}
