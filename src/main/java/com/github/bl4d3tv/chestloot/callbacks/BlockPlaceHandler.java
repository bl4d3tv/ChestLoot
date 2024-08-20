package com.github.bl4d3tv.chestloot.callbacks;

import com.github.bl4d3tv.chestloot.mixed.ChestBlockEntityMixed;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockPlaceHandler implements BlockPlaceCallback {
    @Override
    public ActionResult place(World world, BlockPos pos, BlockState blockState, @Nullable BlockEntity blockEntity, @Nullable PlayerEntity player) {
        // If the world is client-side, the player is null, or the player is in creative mode, early return
        if (world.isClient() || player == null || player.isCreative()) {
            return ActionResult.PASS;
        }

        // Set the block to used if it's a chest
        if (blockEntity instanceof ChestBlockEntity chestBlockEntity && chestBlockEntity instanceof ChestBlockEntityMixed chestBlockEntityMixed) {
            chestBlockEntityMixed.chestloot$setUsed(true);
        }

        return ActionResult.PASS;
    }
}
