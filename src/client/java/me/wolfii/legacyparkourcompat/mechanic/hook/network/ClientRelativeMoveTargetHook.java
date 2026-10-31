package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** Chooses the interpolation target for a position-bearing relative entity move. */
@MechanicType("client_packet.relative_move_target")
public interface ClientRelativeMoveTargetHook extends VersionedMechanic {
    Vec3 target(Entity entity, long xa, long ya, long za, Vec3 nativeTarget);
}
