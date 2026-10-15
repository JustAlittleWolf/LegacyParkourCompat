package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Equality of requested/resolved horizontal movement for collision flags. */
@MechanicType("player.collision.horizontal_comparison")
public interface HorizontalCollisionComparisonHook extends VersionedMechanic {
    boolean equal(double requested, double resolved);
}
