package me.wolfii.legacyparkourcompat.mechanic;

import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;

/** Marks the nested fluid refresh invoked by LivingEntity.checkFallDamage. */
public final class PostMoveFluidRefreshContext {
    private static final ThreadLocal<Refresh> ACTIVE_REFRESH = new ThreadLocal<>();

    private PostMoveFluidRefreshContext() {
    }

    public static <T> T run(Entity entity, boolean suppressLavaCurrent, boolean retainLavaDepth, Supplier<T> action) {
        Refresh previous = ACTIVE_REFRESH.get();
        ACTIVE_REFRESH.set(new Refresh(entity, suppressLavaCurrent, retainLavaDepth));
        try {
            return action.get();
        } finally {
            if (previous == null) {
                ACTIVE_REFRESH.remove();
            } else {
                ACTIVE_REFRESH.set(previous);
            }
        }
    }

    public static boolean suppressesLavaCurrent(Entity entity) {
        Refresh refresh = ACTIVE_REFRESH.get();
        return refresh != null && refresh.entity() == entity && refresh.suppressLavaCurrent();
    }

    public static boolean retainsLavaDepth(Entity entity) {
        Refresh refresh = ACTIVE_REFRESH.get();
        return refresh != null && refresh.entity() == entity && refresh.retainLavaDepth();
    }

    private record Refresh(Entity entity, boolean suppressLavaCurrent, boolean retainLavaDepth) {}
}
