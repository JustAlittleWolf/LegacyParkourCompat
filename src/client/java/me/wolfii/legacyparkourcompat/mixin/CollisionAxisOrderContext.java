package me.wolfii.legacyparkourcompat.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import me.wolfii.legacyparkourcompat.mechanic.CollisionShapeSourceContext;

import java.util.function.Supplier;

/** Keeps the entity scope limited to one collision resolution, including nested collisions. */
final class CollisionAxisOrderContext {
    private static final ThreadLocal<Entity> ENTITY = new ThreadLocal<>();
    private static final ThreadLocal<AABB> RESOLVED_BOX = new ThreadLocal<>();

    private CollisionAxisOrderContext() {
    }

    static Entity currentEntity() {
        return ENTITY.get();
    }

    static void resolvedBox(AABB box) {
        RESOLVED_BOX.set(box);
    }

    static AABB resolvedBox() {
        return RESOLVED_BOX.get();
    }

    static <T> T withEntity(Entity entity, Supplier<T> action) {
        Entity previousEntity = ENTITY.get();
        AABB previousBox = RESOLVED_BOX.get();
        ENTITY.set(entity);
        RESOLVED_BOX.remove();
        try {
            return CollisionShapeSourceContext.withFrame(action);
        } finally {
            if (previousBox == null) {
                RESOLVED_BOX.remove();
            } else {
                RESOLVED_BOX.set(previousBox);
            }
            if (previousEntity == null) {
                ENTITY.remove();
            } else {
                ENTITY.set(previousEntity);
            }
        }
    }
}
