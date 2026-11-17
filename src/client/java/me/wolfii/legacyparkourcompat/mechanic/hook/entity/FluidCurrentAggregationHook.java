package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/**
 * Historical fluid-cell admission and current-vector aggregation for one entity scan.
 * The caller retains responsibility for the native loaded-area gate and for applying
 * the returned vector to the entity's velocity.
 */
@MechanicType("entity.fluid_current_aggregation")
public interface FluidCurrentAggregationHook extends VersionedMechanic {
    /**
     * A tagged-fluid cell and its native flow vector, sampled by the caller inside
     * the contracted entity box.
     */
    record Contribution(int cellY, float fluidHeight, Vec3 flow) {
    }

    /**
     * Filters cells whose surface is below the box's feet and builds the current
     * vector. For a player, the averaged vector remains unnormalized.
     *
     * @param boxMinY lower face of the contracted scan box
     * @param contributions candidate tagged-fluid cells in native scan order
     * @param player whether the scanned entity is a player
     * @return the averaged current vector, or {@link Vec3#ZERO} when no cell qualifies
     */
    Vec3 aggregate(double boxMinY, Iterable<Contribution> contributions, boolean player);
}
