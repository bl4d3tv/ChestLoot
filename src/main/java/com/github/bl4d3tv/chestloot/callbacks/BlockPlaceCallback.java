package com.github.bl4d3tv.chestloot.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Callback for when a block is placed in the world.
 */
public interface BlockPlaceCallback {
    Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class,
            (listeners) -> (world, pos, blockState, blockEntity, player) -> {
                for (BlockPlaceCallback listener : listeners) {
                    ActionResult result = listener.place(world, pos, blockState, blockEntity, player);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    /**
     * Callback for when a block is placed in the world.
     * @param world The world the block is being placed in
     * @param pos The position the block is being placed at
     * @param blockState The state of the block being placed
     * @param blockEntity The block entity being placed, if any
     * @param player The player placing the block, if any
     * @return The result of the block placement
     */
    ActionResult place(World world, BlockPos pos, BlockState blockState, @Nullable BlockEntity blockEntity, @Nullable PlayerEntity player);
}
