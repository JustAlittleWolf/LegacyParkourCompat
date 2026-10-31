package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.RelativePositionReconstructionHook;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Keeps the native codec before the 1.19.2 Mojmap VecDeltaCodec.encode/decode floor behavior,
 * compared with 1.19.3's Math.round encoder; ClientPacketListener.handleMoveEntity consumes
 * that decoder in both releases.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class RelativePositionReconstructionBarrier implements RelativePositionReconstructionHook {
    @Override
    public Optional<Vec3> decode(Vec3 base, long dx, long dy, long dz) {
        return Optional.empty();
    }
}
