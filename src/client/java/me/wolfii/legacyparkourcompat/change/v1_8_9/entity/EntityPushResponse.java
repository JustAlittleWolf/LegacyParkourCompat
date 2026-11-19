package me.wolfii.legacyparkourcompat.change.v1_8_9.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityPushHook;

/**
 * In 1.8.9 Ornithe Feather build.2, LivingEntity.mobTick only calls
 * pushAwayCollidingEntities on the server. In 1.9 Ornithe Feather build.2,
 * the same call also runs on the client and LivingEntity.pushAway dispatches
 * to Entity.push(Entity), which can write to either participant.
 * In 26.2 unobfuscated, LivingEntity.doPush dispatches ordinary collisions
 * to Entity.push(Entity). Skip its local-player write in either role.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class EntityPushResponse implements EntityPushHook {
    @Override
    public boolean suppressCollisionPush() {
        return true;
    }
}
