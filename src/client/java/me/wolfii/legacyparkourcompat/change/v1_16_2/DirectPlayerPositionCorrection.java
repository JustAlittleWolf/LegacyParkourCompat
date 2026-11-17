package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.DirectPlayerPositionCorrectionDelta;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.DirectPlayerPositionCorrectionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

/**
 * Mojmap 1.16.1 and 1.16.5 ClientPacketListener#handleMovePlayer remove an
 * existing vehicle before applying a correction when tickCount is positive.
 * The earlier V1_15_2 provider keeps older selected profiles on their verified
 * no-action behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class DirectPlayerPositionCorrection implements DirectPlayerPositionCorrectionHook {
    public DirectPlayerPositionCorrection() {
    }

    @Override
    public boolean applyToPassenger() {
        return true;
    }

    @Override
    public void preparePassengerCorrection(Entity player) {
        if (player.getVehicle() != null && player.tickCount > 0) {
            player.removeVehicle();
        }
    }

    @Override
    public Vec3 correctedDeltaMovement(Vec3 previousDeltaMovement, Set<Relative> relativePositionAxes) {
        return DirectPlayerPositionCorrectionDelta.corrected(previousDeltaMovement, relativePositionAxes);
    }
}
