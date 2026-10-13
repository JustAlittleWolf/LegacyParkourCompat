package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PlayerCorrectionPositionHook;
import net.minecraft.world.phys.Vec3;

/**
 * 1.19.2 Mojmap: ClientPacketListener.handleMovePlayer calls Entity.absMoveTo(double,
 * double, double, float, float); Entity.absMoveTo(double, double, double) clamps X/Z
 * with Mth.clamp(double, double, double), leaving Y unchanged.
 * 1.19.3 Mojmap: ClientPacketListener.handleMovePlayer calls Entity.setPos(double,
 * double, double) instead, retaining the resolved X/Z coordinates.
 * 1.9 Ornithe Feather: Entity.updatePositionAndAngles(double, double, double, float,
 * float) introduces the same clamp through MathHelper.clamp(double, double, double).
 * 1.8.9 Ornithe Feather: that member writes X/Z directly, establishing the lower gate.
 *
 * This operation restores only current position and its bounding box. The older
 * helpers also write position history; those writes are outside this delta.
 * Finite out-of-range corrections from external/custom servers witness the change;
 * vanilla TeleportCommand.performTeleport in 1.19.2/1.19.3 Mojmap rejects positions
 * outside Level.isInSpawnableBounds before sending the correction.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class PlayerCorrectionCoordinateClamp implements PlayerCorrectionPositionHook {
    @Override
    public Vec3 correctionPosition(Vec3 position, ParkourVersion selected) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return position;
        }
        double x = clampCoordinate(position.x);
        double z = clampCoordinate(position.z);
        return new Vec3(x, position.y, z);
    }

    private static double clampCoordinate(double coordinate) {
        if (coordinate < -3.0E7) {
            return -3.0E7;
        } else {
            return coordinate > 3.0E7 ? 3.0E7 : coordinate;
        }
    }
}
