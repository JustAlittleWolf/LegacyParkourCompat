package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.DirectPlayerPositionCorrectionDelta;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.DirectPlayerPositionCorrectionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

/**
 * Mojmap 1.15.2 ClientPacketListener#handleMovePlayer has no receiver-level
 * vehicle action. The accepted source representatives through 1.15.2 still
 * apply the correction to passengers and bind velocity to position-relative axes.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class DirectPlayerPositionCorrection implements DirectPlayerPositionCorrectionHook {
    public DirectPlayerPositionCorrection() {
    }

    @Override
    public boolean applyToPassenger() {
        return true;
    }

    @Override
    public void preparePassengerCorrection(Entity player) {
    }

    @Override
    public Vec3 correctedDeltaMovement(Vec3 previousDeltaMovement, Set<Relative> relativePositionAxes) {
        return DirectPlayerPositionCorrectionDelta.corrected(previousDeltaMovement, relativePositionAxes);
    }
}
