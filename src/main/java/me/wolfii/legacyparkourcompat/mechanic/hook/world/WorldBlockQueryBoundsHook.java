package me.wolfii.legacyparkourcompat.mechanic.hook.world;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Inclusive coordinate bounds used to enumerate block candidates for
 * local-player auto-jump. The consumer retains the world query's chunk,
 * block-state, boundary, and collision-context filters.
 */
@MechanicType("world.block_query_bounds")
public interface WorldBlockQueryBoundsHook extends VersionedMechanic {
    /** Empty retains native enumeration for profiles outside the source-backed range. */
    Optional<BlockQueryBounds> bounds(AABB queryBox);
}
