package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

/** Keeps the entity scope limited to one collision resolution, including nested collisions. */
public final class CollisionAxisOrderContext {
    private static final ThreadLocal<Entity> ENTITY = new ThreadLocal<>();
    private static final ThreadLocal<AABB> RESOLVED_BOX = new ThreadLocal<>();
    private static final ThreadLocal<MovementQuery> MOVEMENT_QUERY = new ThreadLocal<>();
    private static final ThreadLocal<VoxelShape> COLLECTED_BLOCK_SHAPE = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> BLOCK_COLLECTION_DISABLED = new ThreadLocal<>();

    /** Actual post-adjustment vector passed to the primary {@code Entity.collide} call. */
    public record MovementQuery(Entity entity, AABB startingBox, Vec3 requestedMovement,
                                boolean unadjustedPrimaryMovement) {
        public AABB sweptBox() {
            return this.startingBox.expandTowards(this.requestedMovement);
        }
    }

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

    public static MovementQuery movementQuery() {
        return MOVEMENT_QUERY.get();
    }

    public static void collectedBlockShape(VoxelShape shape) {
        COLLECTED_BLOCK_SHAPE.set(shape);
    }

    public static VoxelShape collectedBlockShape() {
        return COLLECTED_BLOCK_SHAPE.get();
    }

    public static void clearCollectedBlockShape() {
        COLLECTED_BLOCK_SHAPE.remove();
    }

    public static boolean blockCollectionDisabled() {
        return Boolean.TRUE.equals(BLOCK_COLLECTION_DISABLED.get());
    }

    public static <T> T withBlockCollectionDisabled(Supplier<T> action) {
        Boolean previousDisabled = BLOCK_COLLECTION_DISABLED.get();
        VoxelShape previousShape = COLLECTED_BLOCK_SHAPE.get();
        BLOCK_COLLECTION_DISABLED.set(true);
        COLLECTED_BLOCK_SHAPE.remove();
        try {
            return action.get();
        } finally {
            if (previousDisabled == null) {
                BLOCK_COLLECTION_DISABLED.remove();
            } else {
                BLOCK_COLLECTION_DISABLED.set(previousDisabled);
            }
            if (previousShape == null) {
                COLLECTED_BLOCK_SHAPE.remove();
            } else {
                COLLECTED_BLOCK_SHAPE.set(previousShape);
            }
        }
    }

    public static <T> T withMovementEntity(Entity entity, AABB startingBox, Vec3 requestedMovement,
                                          boolean unadjustedPrimaryMovement,
                                          Supplier<T> action) {
        MovementQuery previousQuery = MOVEMENT_QUERY.get();
        VoxelShape previousShape = COLLECTED_BLOCK_SHAPE.get();
        MOVEMENT_QUERY.set(new MovementQuery(entity, startingBox, requestedMovement, unadjustedPrimaryMovement));
        COLLECTED_BLOCK_SHAPE.remove();
        try {
            return withEntity(entity, action);
        } finally {
            if (previousQuery == null) {
                MOVEMENT_QUERY.remove();
            } else {
                MOVEMENT_QUERY.set(previousQuery);
            }
            if (previousShape == null) {
                COLLECTED_BLOCK_SHAPE.remove();
            } else {
                COLLECTED_BLOCK_SHAPE.set(previousShape);
            }
        }
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
