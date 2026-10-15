package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalCollisionComparisonHook;

/**
 * Ornithe Feather 1.8.9 and 1.13.2 Entity.move set collidingHorizontally
 * from exact requested/resolved X or Z inequality. Ornithe Feather 1.14
 * Entity.move uses MathHelper.m_68886716, a 1.0E-5 approximate comparison;
 * 26.2 unobfuscated Entity.move retains it as Mth.equal. Replace only those
 * comparisons; preserve native short circuit and collision consumers. Exact
 * equality also preserves the historical signed-zero and NaN comparison.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class HorizontalCollisionComparison implements HorizontalCollisionComparisonHook {
    @Override
    public boolean equal(double requested, double resolved) {
        return requested == resolved;
    }
}
