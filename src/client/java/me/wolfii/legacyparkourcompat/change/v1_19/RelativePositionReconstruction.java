package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.RelativePositionReconstructionHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * 1.19.2 Mojmap VecDeltaCodec.encode/decode floors the scaled base; 1.19.3 changes encode to
 * Math.round. ClientPacketListener.handleMoveEntity decodes before setting the codec base in both
 * releases (1.19.2 lines 523-544; 1.19.3 lines 552-573). The 1.19/1.19.1 source alias review
 * verifies the floor operation is unchanged across that earlier group.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class RelativePositionReconstruction implements RelativePositionReconstructionHook {
    @Override
    public Optional<Vec3> decode(Vec3 base, long dx, long dy, long dz) {
        return Optional.of(new Vec3(
            dx == 0L ? base.x : decodeAxis(base.x, dx),
            dy == 0L ? base.y : decodeAxis(base.y, dy),
            dz == 0L ? base.z : decodeAxis(base.z, dz)
        ));
    }

    private static double decodeAxis(double base, long delta) {
        return (Mth.lfloor(base * 4096.0) + delta) / 4096.0;
    }
}
