package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.world.entity.Entity;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.WorldBorderCollisionHook;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/** Records the border admission and actual shape within one bounding-box call. */
public final class CollisionBorderShapeContext {
    private static final ThreadLocal<Frame> CURRENT = new ThreadLocal<>();

    private CollisionBorderShapeContext() {}

    public static final class Frame {
        private final @Nullable Entity entity;
        private boolean historicallyAdmitted;
        private boolean nativelyAdmitted;
        private @Nullable VoxelShape admittedShape;
        private @Nullable WorldBorderCollisionHook.Collision historicalCollision;

        private Frame(@Nullable Entity entity) {
            this.entity = entity;
        }

        public void recordAdmission(Entity source, boolean historical, boolean nativeAdmission) {
            if (source == entity) {
                historicallyAdmitted = historical;
                nativelyAdmitted = nativeAdmission;
            }
        }

        public void recordShape(Entity source, VoxelShape shape) {
            if (source == entity) {
                admittedShape = shape;
            }
        }

        public void recordHistoricalCollision(Entity source, WorldBorderCollisionHook.Collision collision,
                                              boolean nativeAdmission) {
            if (source == entity) {
                historicalCollision = collision;
                historicallyAdmitted = collision.insideCloseToBorder();
                nativelyAdmitted = nativeAdmission;
                admittedShape = collision.shape();
            }
        }

        public @Nullable WorldBorderCollisionHook.Collision historicalCollision(Entity source) {
            return source == entity ? historicalCollision : null;
        }

        public boolean isSoleHistoricalBorder(List<VoxelShape> shapes) {
            return historicallyAdmitted && !nativelyAdmitted && shapes.size() == 1
                && shapes.getFirst() == admittedShape;
        }
    }

    public static @Nullable Frame current() {
        return CURRENT.get();
    }

    public static <T> T withFrame(@Nullable Entity entity, Supplier<T> action) {
        Frame previous = CURRENT.get();
        CURRENT.set(new Frame(entity));
        try {
            return action.get();
        } finally {
            if (previous == null) {
                CURRENT.remove();
            } else {
                CURRENT.set(previous);
            }
        }
    }
}
