package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** The vertical acceleration term before subsequent fall-flying velocity corrections. */
@MechanicType("player.glide.vertical_acceleration")
public interface GlideVerticalAccelerationHook extends VersionedMechanic {
    double verticalAcceleration(ParkourVersion selected, double liftForce, double gravity,
                                boolean slowFalling, double vanilla);
}
