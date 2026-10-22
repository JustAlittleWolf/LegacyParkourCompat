package me.wolfii.legacyparkourcompat.mixin;

import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

/** Keeps the entity scope limited to one collision resolution, including nested collisions. */
final class CollisionAxisOrderContext {
    private static final ThreadLocal<Entity> ENTITY = new ThreadLocal<>();

    private CollisionAxisOrderContext() {
    }

    static Entity currentEntity() {
        return ENTITY.get();
    }

    static <T> T withEntity(Entity entity, Supplier<T> action) {
        Entity previousEntity = ENTITY.get();
        ENTITY.set(entity);
        try {
            return action.get();
        } finally {
            if (previousEntity == null) {
                ENTITY.remove();
            } else {
                ENTITY.set(previousEntity);
            }
        }
    }
}
