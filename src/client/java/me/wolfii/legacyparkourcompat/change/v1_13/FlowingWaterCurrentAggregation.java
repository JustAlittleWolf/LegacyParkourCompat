package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.FluidCurrentAggregationHook;
import net.minecraft.world.phys.Vec3;

/**
 * Matches the 1.13 fluid current scan and player vector response.
 *
 * <p>Source comparison: Ornithe Feather 1.12.2
 * {@code decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/Entity.java#checkWaterCollisions}
 * and {@code .../net/minecraft/world/World.java#applyLiquidDrag} versus Ornithe Feather 1.13.0
 * {@code decompiled_minecraft/1.13/ornithe-feather/net/minecraft/entity/Entity.java#m_69693160}
 * and {@code #checkWaterState}. In 1.13 the scan
 * admits fluid surfaces at the contracted box's lower face, scales each
 * contributor by the running immersion depth while shallow, averages the
 * contributors, and leaves the player vector unnormalized. In 1.12.2 the
 * operation used the rounded-top predicate and normalized its summed flow.
 */
// V1_13_1 is the nearest later annotated profile that still covers the exact
// 1.13.0 behavior; 1.13.1/1.13.2 were source-checked unchanged.
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class FlowingWaterCurrentAggregation implements FluidCurrentAggregationHook {
    private static final double SHALLOW_DEPTH = 0.4;

    public FlowingWaterCurrentAggregation() {
    }

    @Override
    public Vec3 aggregate(double boxMinY, Iterable<Contribution> contributions, boolean player) {
        double immersionDepth = 0.0;
        Vec3 total = Vec3.ZERO;
        int contributors = 0;

        for (Contribution contribution : contributions) {
            // Keep the source int + float addition in float precision before
            // widening it into this double local.
            double surfaceY = contribution.cellY() + contribution.fluidHeight();
            if (surfaceY < boxMinY) {
                continue;
            }

            immersionDepth = Math.max(immersionDepth, surfaceY - boxMinY);
            Vec3 flow = contribution.flow();
            if (immersionDepth < SHALLOW_DEPTH) {
                flow = flow.scale(immersionDepth);
            }
            total = total.add(flow);
            contributors++;
        }

        if (contributors == 0) {
            return Vec3.ZERO;
        }

        Vec3 average = total.scale(1.0 / contributors);
        return player ? average : average.normalize();
    }
}
