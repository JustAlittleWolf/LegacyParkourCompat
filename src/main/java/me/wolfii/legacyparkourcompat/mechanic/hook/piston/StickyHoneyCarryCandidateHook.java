package me.wolfii.legacyparkourcompat.mechanic.hook.piston;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

/** Resolves admission for an entity tested by the native sticky-honey predicate. */
@MechanicType("piston.sticky_honey_carry_candidate")
public interface StickyHoneyCarryCandidateHook extends VersionedMechanic {
    /**
     * Returns whether the entity should be admitted by the selected release's predicate.
     * The native result is supplied for identity implementations, and the bounds are the
     * running version's entity query box.
     */
    boolean isCarryCandidate(boolean nativeCandidate, AABB nativeScanBounds, Entity entity, BlockPos pistonPos);
}
