package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

/** Scoped consumer identity; nested calls and exceptional exits restore the caller. */
public final class BorderAdmissionContext {
    private static final ThreadLocal<CollisionAdmission> COLLISION = new ThreadLocal<>();
    private static final ThreadLocal<Entity> WAKING_PLAYER = new ThreadLocal<>();

    private BorderAdmissionContext() {}

    public record CollisionAdmission(Entity entity, boolean includeBorder) {}

    public static @Nullable CollisionAdmission collision() {
        return COLLISION.get();
    }

    public static @Nullable Entity wakingPlayer() {
        return WAKING_PLAYER.get();
    }

    public static <T> T withCollision(@Nullable CollisionAdmission admission, Supplier<T> action) {
        CollisionAdmission previous = COLLISION.get();
        set(COLLISION, admission);
        try {
            return action.get();
        } finally {
            set(COLLISION, previous);
        }
    }

    public static void withWakingPlayer(@Nullable Entity player, Runnable action) {
        Entity previous = WAKING_PLAYER.get();
        set(WAKING_PLAYER, player);
        try {
            action.run();
        } finally {
            set(WAKING_PLAYER, previous);
        }
    }

    private static <T> void set(ThreadLocal<T> context, @Nullable T value) {
        if (value == null) {
            context.remove();
        } else {
            context.set(value);
        }
    }
}
