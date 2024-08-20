package com.github.bl4d3tv.chestloot.mixin;

import com.github.bl4d3tv.chestloot.mixed.ChestBlockEntityMixed;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin extends BlockEntity implements ChestBlockEntityMixed {
    @Unique
    private boolean chestloot$Used = false;

    public ChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(at = @At("TAIL"), method = "readNbt")
    private void chestloot$readUsedNbt(NbtCompound nbt, CallbackInfo ci) {
        this.chestloot$Used = nbt.getBoolean("Used");
    }

    @Inject(at = @At("TAIL"), method = "writeNbt")
    private void chestloot$writeUsedNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("Used", this.chestloot$Used);
    }

    @Override
    public boolean chestloot$isUsed() {
        return this.chestloot$Used;
    }

    @Override
    public void chestloot$setUsed(boolean used) {
        this.chestloot$Used = used;
        this.markDirty();
    }
}
