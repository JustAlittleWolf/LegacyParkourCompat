package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.DirectPlayerPositionCorrectionDelta;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.DirectPlayerPositionCorrectionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

/**
 * Mojmap 1.21.1 ClientPacketListener#handleMovePlayer applies corrections to a
 * passenger and uses position-relative flags to preserve the matching velocity
 * axes. Mojmap 1.21.2 and 1.21.3 add the passenger gate and use separate delta
 * flags in PositionMoveRotation#calculateAbsolute; their ServerPlayer#teleportTo
 * writer also marks DELTA relative. Restore the 1.21.1 receiver response.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class DirectPlayerPositionCorrection implements DirectPlayerPositionCorrectionHook {
    public DirectPlayerPositionCorrection() {
    }

    @Override
    public boolean applyToPassenger() {
        return true;
    }

    @Override
    public void preparePassengerCorrection(Entity player) {
        // Mojmap 1.21.1 has no receiver-level dismount action for this correction.
    }

    @Override
    public Vec3 correctedDeltaMovement(Vec3 previousDeltaMovement, Set<Relative> relativePositionAxes) {
        return DirectPlayerPositionCorrectionDelta.corrected(previousDeltaMovement, relativePositionAxes);
    }
}
