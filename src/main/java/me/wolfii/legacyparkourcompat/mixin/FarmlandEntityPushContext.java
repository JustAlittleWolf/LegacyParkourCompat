package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FarmlandEntityPushBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.function.Supplier;

/** Limits the farmland-specific legacy exception to Block.pushEntitiesUp's call. */
final class FarmlandEntityPushContext {
    private static final ThreadLocal<Integer> DEPTH = new ThreadLocal<>();

    private FarmlandEntityPushContext() {
    }

    static <T> T around(Supplier<T> operation) {
        Integer currentDepth = DEPTH.get();
        int previousDepth = currentDepth == null ? 0 : currentDepth;
        DEPTH.set(previousDepth + 1);
        try {
            return operation.get();
        } finally {
            if (previousDepth == 0) {
                DEPTH.remove();
            } else {
                DEPTH.set(previousDepth);
            }
        }
    }

    static boolean shouldSuppress(Entity entity) {
        Integer depth = DEPTH.get();
        if (depth == null || depth == 0 || !(entity instanceof Player player)) {
            return false;
        }
        Optional<FarmlandEntityPushBehavior> behavior = MovementRuntime.find(FarmlandEntityPushBehavior.class, player);
        return behavior.map(change -> change.suppressPlayerPushUp(player)).orElse(false);
    }
}
