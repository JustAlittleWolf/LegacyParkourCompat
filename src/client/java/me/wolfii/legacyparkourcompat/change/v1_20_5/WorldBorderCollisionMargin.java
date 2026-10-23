package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.WorldBorderCollisionMarginHook;
import net.minecraft.world.level.border.WorldBorder;

/**
 * Minecraft 1.20.5 and 1.20.6, Mojmap:
 * {@code net.minecraft.world.level.border.WorldBorder#isWithinBounds(double, double, double)}
 * uses strict lower X/Z bounds. Minecraft 1.21 (also 1.21.1), Mojmap, changes those
 * comparisons to inclusive bounds while preserving the strict upper bounds.
 * In both versions {@code WorldBorder#isInsideCloseToBorder(Entity, AABB)} calls
 * that point predicate after computing the box margin and passing the distance gate.
 * Only that collision-admission call is replaced; other border predicates stay native.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class WorldBorderCollisionMargin implements WorldBorderCollisionMarginHook {
    @Override
    public boolean isWithinCollisionMargin(WorldBorder border, double x, double z, double margin) {
        return x > border.getMinX() - margin && x < border.getMaxX() + margin
            && z > border.getMinZ() - margin && z < border.getMaxZ() + margin;
    }
}
