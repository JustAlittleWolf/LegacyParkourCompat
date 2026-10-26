package me.wolfii.legacyparkourcompat.mechanic;

import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;

/** Marks the nested fluid refresh invoked by LivingEntity.checkFallDamage. */
public final class PostMoveFluidRefreshContext {
    private static final ThreadLocal<Entity> ACTIVE_ENTITY = new ThreadLocal<>();

    private PostMoveFluidRefreshContext() {
    }

    public static <T> T suppressLavaCurrent(Entity entity, Supplier<T> action) {
        Entity previous = ACTIVE_ENTITY.get();
        ACTIVE_ENTITY.set(entity);
        try {
            return action.get();
        } finally {
            if (previous == null) {
                ACTIVE_ENTITY.remove();
            } else {
                ACTIVE_ENTITY.set(previous);
            }
        }
    }

    public static boolean suppressesLavaCurrent(Entity entity) {
        return ACTIVE_ENTITY.get() == entity;
    }
}
