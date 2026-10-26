package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.world.entity.Entity;

/** Carries a scoped historical current-cutoff bypass into the nested tracker call. */
public final class FluidCurrentCutoffContext {
    private static final ThreadLocal<Entity> ACTIVE_ENTITY = new ThreadLocal<>();

    private FluidCurrentCutoffContext() {
    }

    public static void run(Entity entity, Runnable action) {
        Entity previous = ACTIVE_ENTITY.get();
        ACTIVE_ENTITY.set(entity);
        try {
            action.run();
        } finally {
            if (previous == null) {
                ACTIVE_ENTITY.remove();
            } else {
                ACTIVE_ENTITY.set(previous);
            }
        }
    }

    public static boolean isActiveFor(Entity entity) {
        return ACTIVE_ENTITY.get() == entity;
    }
}
