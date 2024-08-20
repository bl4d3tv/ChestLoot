package com.github.bl4d3tv.chestloot.callbacks;

import com.github.bl4d3tv.chestloot.ChestLoot;
import com.github.bl4d3tv.chestloot.ChestLootConfig;
import com.github.bl4d3tv.chestloot.mixed.ChestBlockEntityMixed;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class UseBlockHandler implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (world.isClient()) {
            return ActionResult.PASS;
        }

        // Check if the block is a chest
        if (world.getBlockEntity(hitResult.getBlockPos()) instanceof ChestBlockEntity chestBlockEntity) {
            // Convert to our mixed chest block entity and early return if it's used
            ChestBlockEntityMixed mixed = (ChestBlockEntityMixed) chestBlockEntity;
            if (mixed.chestloot$isUsed()) {
                return ActionResult.PASS;
            }

            // Get the biome key and ID
            RegistryKey<Biome> biomeKey = world.getBiome(hitResult.getBlockPos()).getKey().orElse(null);
            String biomeId = biomeKey == null ? "default" : biomeKey.getValue().toString();

            // Get the loot tables for the biome
            List<Identifier> tables = ChestLootConfig.getLootForBiome(biomeId);
            if (tables == null || tables.isEmpty()) {
                return ActionResult.PASS;
            }
            ChestLoot.LOGGER.debug("Biome: {} Tables: {}", biomeId, tables);

            // Select a random loot table
            Identifier table = tables.get(world.random.nextInt(tables.size()));
            if (table == null) {
                return ActionResult.PASS;
            }
            ChestLoot.LOGGER.debug("Selected table: {}", table);

            // Set the loot table and mark the chest as used
            LootableContainerBlockEntity.setLootTable(world, world.random, hitResult.getBlockPos(), table);
            mixed.chestloot$setUsed(true);
        }

        return ActionResult.PASS;
    }
}
