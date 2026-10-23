package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.WorldBorderCollisionMarginHook;
import net.minecraft.world.level.border.WorldBorder;

/**
 * Scope fence for versions before the independently verified 1.20.5/1.20.6 interval.
 * This delegates to native behavior without claiming historical parity for that prefix.
 * The verified delta is Minecraft 1.20.5/1.20.6 versus 1.21, Mojmap,
 * {@code net.minecraft.world.level.border.WorldBorder#isWithinBounds(double, double, double)}
 * as called by {@code WorldBorder#isInsideCloseToBorder(Entity, AABB)}.
 * Native Minecraft 26.2, unobfuscated, retains this call and the inclusive predicate.
 * Closest-applicable resolution selects this fence for 1.20.2 and earlier profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class NativeWorldBorderCollisionMargin implements WorldBorderCollisionMarginHook {
    @Override
    public boolean isWithinCollisionMargin(WorldBorder border, double x, double z, double margin) {
        return border.isWithinBounds(x, z, margin);
    }
}
