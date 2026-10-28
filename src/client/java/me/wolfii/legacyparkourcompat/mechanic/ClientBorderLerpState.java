package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.border.WorldBorderCollisionHook;
import net.minecraft.util.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

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

        // Sample once: the admission and collision shape must share this size.
        double elapsed = (Util.getMillis() - lerp.beginMillis) / lerp.durationMillis;
        double size = elapsed < 1.0 ? Mth.lerp(elapsed, lerp.from, lerp.to) : lerp.to;
        double centerX = border.getCenterX();
        double centerZ = border.getCenterZ();
        int absoluteMaxSize = border.getAbsoluteMaxSize();
        double minX = Mth.clamp(centerX - size / 2.0, -absoluteMaxSize, absoluteMaxSize);
        double minZ = Mth.clamp(centerZ - size / 2.0, -absoluteMaxSize, absoluteMaxSize);
        double maxX = Mth.clamp(centerX + size / 2.0, -absoluteMaxSize, absoluteMaxSize);
        double maxZ = Mth.clamp(centerZ + size / 2.0, -absoluteMaxSize, absoluteMaxSize);

        double margin = Math.max(Mth.absMax(box.getXsize(), box.getZsize()), 1.0);
        double distance = Math.min(entity.getX() - minX, maxX - entity.getX());
        distance = Math.min(distance, entity.getZ() - minZ);
        distance = Math.min(distance, maxZ - entity.getZ());
        boolean close = distance < margin * 2.0
            && entity.getX() >= minX - margin
            && entity.getX() < maxX + margin
            && entity.getZ() >= minZ - margin
            && entity.getZ() < maxZ + margin;

        var shape = Shapes.join(
            Shapes.INFINITY,
            Shapes.box(Math.floor(minX), Double.NEGATIVE_INFINITY, Math.floor(minZ),
                Math.ceil(maxX), Double.POSITIVE_INFINITY, Math.ceil(maxZ)),
            BooleanOp.ONLY_FIRST
        );
        return new WorldBorderCollisionHook.Collision(close, shape);
    }

    private record Lerp(double from, double to, double durationMillis, long beginMillis) {
    }
}
