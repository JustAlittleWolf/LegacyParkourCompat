package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Reconstructs the historical decoded position for a received relative-move packet. */
@MechanicType("client_packet.relative_position_reconstruction")
public interface RelativePositionReconstructionHook extends VersionedMechanic {
    Optional<Vec3> decode(Vec3 base, long dx, long dy, long dz);
}
