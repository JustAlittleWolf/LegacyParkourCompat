package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

/** Shared 1.21.1-compatible position-axis velocity response. */
public final class DirectPlayerPositionCorrectionDelta {
    private DirectPlayerPositionCorrectionDelta() {
    }

    public static Vec3 corrected(Vec3 previousDeltaMovement, Set<Relative> relativePositionAxes) {
        return new Vec3(
            relativePositionAxes.contains(Relative.X) ? previousDeltaMovement.x : 0.0,
            relativePositionAxes.contains(Relative.Y) ? previousDeltaMovement.y : 0.0,
            relativePositionAxes.contains(Relative.Z) ? previousDeltaMovement.z : 0.0
        );
    }
}
