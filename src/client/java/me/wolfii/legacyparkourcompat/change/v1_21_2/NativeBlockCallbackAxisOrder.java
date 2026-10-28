package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCallbackAxisOrderHook;
import net.minecraft.world.phys.Vec3;

/** Keeps the pre-1.21.4 callback axis operand native. */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativeBlockCallbackAxisOrder implements BlockCallbackAxisOrderHook {
    @Override
    public Vec3 callbackAxisOrder(Vec3 recordedMovement, Vec3 resolvedMovement) {
        return recordedMovement;
    }
}
