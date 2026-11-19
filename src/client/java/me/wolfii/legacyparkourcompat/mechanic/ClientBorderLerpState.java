package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.border.WorldBorderCollisionHook;
import net.minecraft.util.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.WeakHashMap;

/** Client packet timing metadata; vanilla border state remains untouched. */
public final class ClientBorderLerpState {
    private static final double MILLIS_PER_SECOND = 1_000.0;
    private static final double TICKS_PER_SECOND = 20.0;
    private static final Map<WorldBorder, Lerp> LERPS = new WeakHashMap<>();

    private ClientBorderLerpState() {
    }

    public static void begin(WorldBorder border, double from, double to, long durationTicks) {
        clear(border);
        if (durationTicks > 0L) {
            // 26.2 TimeArgument maps seconds to 20 ticks; the 1.21.10 command
            // passed seconds * 1000 as the elapsed-millisecond duration.
            double durationMillis = durationTicks * MILLIS_PER_SECOND / TICKS_PER_SECOND;
            LERPS.put(border, new Lerp(from, to, durationMillis, Util.getMillis()));
        }
    }

    public static void clear(WorldBorder border) {
        LERPS.remove(border);
    }

    public static void clearAll() {
        LERPS.clear();
    }

    public static WorldBorderCollisionHook.Collision collision(WorldBorder border, Entity entity, AABB box) {
        Lerp lerp = LERPS.get(border);
        if (lerp == null) {
            return null;
        }

        double margin = Math.max(Mth.absMax(box.getXsize(), box.getZsize()), 1.0);
        boolean close = lerp.distanceToBorder(border, entity.getX(), entity.getZ()) < margin * 2.0
            && lerp.isWithinBounds(border, entity.getX(), entity.getZ(), margin);
        return new WorldBorderCollisionHook.Collision(close, () -> lerp.shape(border));
    }

    private record Lerp(double from, double to, double durationMillis, long beginMillis) {
        // 1.21.4–1.21.10 Mojmap WorldBorder.MovingBorderExtent#getSize reads the clock
        // on each bound access, including the separate shape read in Entity#collectColliders.
        private double size() {
            double elapsed = (Util.getMillis() - beginMillis) / durationMillis;
            return elapsed < 1.0 ? Mth.lerp(elapsed, from, to) : to;
        }

        private double min(double center, int limit) {
            return Mth.clamp(center - size() / 2.0, -limit, limit);
        }

        private double max(double center, int limit) {
            return Mth.clamp(center + size() / 2.0, -limit, limit);
        }

        private double distanceToBorder(WorldBorder border, double x, double z) {
            int limit = border.getAbsoluteMaxSize();
            double fromMinZ = z - min(border.getCenterZ(), limit);
            double fromMaxZ = max(border.getCenterZ(), limit) - z;
            double fromMinX = x - min(border.getCenterX(), limit);
            double fromMaxX = max(border.getCenterX(), limit) - x;
            return Math.min(Math.min(Math.min(fromMinX, fromMaxX), fromMinZ), fromMaxZ);
        }

        private boolean isWithinBounds(WorldBorder border, double x, double z, double margin) {
            int limit = border.getAbsoluteMaxSize();
            return x >= min(border.getCenterX(), limit) - margin
                && x < max(border.getCenterX(), limit) + margin
                && z >= min(border.getCenterZ(), limit) - margin
                && z < max(border.getCenterZ(), limit) + margin;
        }

        private VoxelShape shape(WorldBorder border) {
            int limit = border.getAbsoluteMaxSize();
            return Shapes.join(Shapes.INFINITY, Shapes.box(
                Math.floor(min(border.getCenterX(), limit)), Double.NEGATIVE_INFINITY,
                Math.floor(min(border.getCenterZ(), limit)), Math.ceil(max(border.getCenterX(), limit)),
                Double.POSITIVE_INFINITY, Math.ceil(max(border.getCenterZ(), limit))), BooleanOp.ONLY_FIRST);
        }
    }
}
