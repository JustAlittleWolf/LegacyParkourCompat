package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_21_11.LegacyBlockRestitution;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;

/** Pre-1.14 movement independently cleared both clipped velocity components. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class Pre114CollisionVelocity extends LegacyBlockRestitution {
}
