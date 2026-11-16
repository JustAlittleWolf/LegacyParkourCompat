package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.world.BlockQueryBounds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

/** Scopes historical bounds to one local-player auto-jump block-collision query. */
public final class WorldBlockQueryBoundsContext {
    private static final ThreadLocal<Request> CURRENT = new ThreadLocal<>();

    private WorldBlockQueryBoundsContext() {
    }

    public static <T> T with(CollisionGetter level, Entity player, AABB query,
                             BlockQueryBounds bounds, Supplier<T> operation) {
        Request previous = CURRENT.get();
        CURRENT.set(new Request(level, player, query, bounds));
        try {
            return operation.get();
        } finally {
            if (previous == null) {
                CURRENT.remove();
            } else {
                CURRENT.set(previous);
            }
        }
    }

    public static @Nullable BlockQueryBounds forQuery(CollisionGetter level, Entity player, AABB query) {
        Request request = CURRENT.get();
        return request != null && request.level() == level && request.player() == player && request.query() == query
            ? request.bounds() : null;
    }

    private record Request(CollisionGetter level, Entity player, AABB query, BlockQueryBounds bounds) {
    }
}
