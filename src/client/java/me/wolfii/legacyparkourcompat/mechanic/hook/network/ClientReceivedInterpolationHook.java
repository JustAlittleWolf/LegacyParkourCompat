package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** Queues and advances the local player's received-packet interpolation target. */
@MechanicType("client_packet.interpolation")
public interface ClientReceivedInterpolationHook extends VersionedMechanic {
    /** Whether this resolved change replaces the current receiver's local-player packet policy. */
    boolean handlesLegacyPackets(Entity entity);

    boolean queue(Entity entity, Vec3 target, float yRot, float xRot, int steps);

    boolean pending(Entity entity);

    void advance(Entity entity);
}
