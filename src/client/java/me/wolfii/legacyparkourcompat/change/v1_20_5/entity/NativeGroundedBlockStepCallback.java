package me.wolfii.legacyparkourcompat.change.v1_20_5.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.GroundedBlockStepCallbackHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

/** Keeps native 1.20.5-1.20.6 grounded step callback timing. */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class NativeGroundedBlockStepCallback implements GroundedBlockStepCallbackHook {
    @Override
    public void afterGroundState(Entity entity, BlockPos pos, BlockState state) {
    }

    @Override
    public boolean suppressPostTravel(Entity entity) {
        return false;
    }
}
