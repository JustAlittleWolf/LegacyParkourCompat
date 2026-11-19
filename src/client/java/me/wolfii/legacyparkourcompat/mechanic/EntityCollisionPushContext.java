package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityPushHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

/** Carries the collision dispatch participants to the two velocity writes. */
public final class EntityCollisionPushContext {
    private static final ThreadLocal<CollisionPush> CURRENT = new ThreadLocal<>();

    private EntityCollisionPushContext() {
    }

    public static void dispatch(Entity receiver, Entity pusher, VanillaCall push) {
        Entity candidate = receiver instanceof LocalPlayer ? receiver : pusher;
        if (!(candidate instanceof LocalPlayer player)
            || !MovementRuntime.find(EntityPushHook.class, player)
                .map(EntityPushHook::suppressCollisionPush)
                .orElse(false)) {
            push.run();
            return;
        }

        CollisionPush previous = CURRENT.get();
        CURRENT.set(new CollisionPush(receiver, pusher, player));
        try {
            push.run();
        } finally {
            if (previous == null) {
                CURRENT.remove();
            } else {
                CURRENT.set(previous);
            }
        }
    }

    public static boolean suppressVelocityWrite(Entity receiver, Entity pusher, Entity recipient) {
        CollisionPush push = CURRENT.get();
        return push != null && push.receiver() == receiver && push.pusher() == pusher
            && push.player() == recipient;
    }

    private record CollisionPush(Entity receiver, Entity pusher, LocalPlayer player) {}
}
