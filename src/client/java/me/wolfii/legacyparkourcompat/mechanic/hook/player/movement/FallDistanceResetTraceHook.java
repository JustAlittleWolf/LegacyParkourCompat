package me.wolfii.legacyparkourcompat.mechanic.hook.player.movement;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/** Selects the displacement used to trace blocks and water that reset fall distance. */
@MechanicType("player.movement.fall_distance_reset_trace")
public interface FallDistanceResetTraceHook extends VersionedMechanic {
    Vec3 fallResetTraceDisplacement(Vec3 resolvedDisplacement, Vec3 nativeTraceDisplacement);
}
