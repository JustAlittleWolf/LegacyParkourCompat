package me.wolfii.legacyparkourcompat.mixin;

import net.minecraft.world.entity.Entity;

/** Carries an already-resolved shallow WATER call into its nested tracker invocation. */
final class ShallowWaterCurrentContext {
    private static final ThreadLocal<Entity> ACTIVE_ENTITY = new ThreadLocal<>();

    private ShallowWaterCurrentContext() {
    }

    static void run(Entity entity, Runnable action) {
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

    static boolean isActiveFor(Entity entity) {
        return ACTIVE_ENTITY.get() == entity;
    }
}
