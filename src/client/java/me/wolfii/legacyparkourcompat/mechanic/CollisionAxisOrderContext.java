package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.function.Supplier;

/** Keeps the entity scope limited to one collision resolution, including nested collisions. */
public final class CollisionAxisOrderContext {
    private static final ThreadLocal<Entity> ENTITY = new ThreadLocal<>();
    private static final ThreadLocal<AABB> RESOLVED_BOX = new ThreadLocal<>();

    private CollisionAxisOrderContext() {
    }

    public static Entity currentEntity() {
        return ENTITY.get();
    }

    public static void resolvedBox(AABB box) {
        RESOLVED_BOX.set(box);
    }

    public static AABB resolvedBox() {
        return RESOLVED_BOX.get();
    }

    public static <T> T withEntity(Entity entity, Supplier<T> action) {
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
