package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

/** Historical passenger admission and velocity handling for a direct player correction. */
@MechanicType("player.position_correction")
public interface DirectPlayerPositionCorrectionHook extends VersionedMechanic {
    boolean applyToPassenger();

    void preparePassengerCorrection(Entity player);

    Vec3 correctedDeltaMovement(Vec3 previousDeltaMovement, Set<Relative> relativePositionAxes);
}
