package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.DirectPlayerPositionCorrectionDelta;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.DirectPlayerPositionCorrectionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

/**
 * Mojmap 1.19.3 ClientPacketListener#handleMovePlayer gates vehicle removal
 * on ClientboundPlayerPositionPacket#requestDismountVehicle. The 26.2 packet
 * has no corresponding value, so preserve the native passenger gate for this
 * selected era instead of inventing a dismount signal. Non-passenger velocity
 * correction still uses the accepted position-axis rule.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class DirectPlayerPositionCorrection implements DirectPlayerPositionCorrectionHook {
    public DirectPlayerPositionCorrection() {
    }

    @Override
    public boolean applyToPassenger() {
        return false;
    }

    @Override
    public void preparePassengerCorrection(Entity player) {
    }

    @Override
    public Vec3 correctedDeltaMovement(Vec3 previousDeltaMovement, Set<Relative> relativePositionAxes) {
        return DirectPlayerPositionCorrectionDelta.corrected(previousDeltaMovement, relativePositionAxes);
    }
}
