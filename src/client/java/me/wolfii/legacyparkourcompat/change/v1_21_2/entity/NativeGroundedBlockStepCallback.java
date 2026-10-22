package me.wolfii.legacyparkourcompat.change.v1_21_2.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.GroundedBlockStepCallbackHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

/** Keeps native post-travel step callbacks from 1.21.2 onward. */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativeGroundedBlockStepCallback implements GroundedBlockStepCallbackHook {
    @Override
    public void afterGroundState(Entity entity, BlockPos pos, BlockState state) {
    }

    @Override
    public boolean suppressPostTravel(Entity entity) {
        return false;
    }
}
