package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** Chooses the interpolation target for a received entity teleport. */
@MechanicType("client_packet.teleport_target")
public interface ClientTeleportTargetHook extends VersionedMechanic {
    Vec3 target(Entity entity, Vec3 packetTarget);
}
