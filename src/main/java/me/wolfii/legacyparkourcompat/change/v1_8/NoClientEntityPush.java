package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.EntityPushBehavior;
import net.minecraft.world.entity.Entity;

/**
 * 1.8 {@code LivingEntity#tickCramming} ran only on the server. 1.9 started
 * pushing the player on the client as well.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class NoClientEntityPush implements EntityPushBehavior {
    @Override
    public boolean allow(Entity player, Entity self, Entity other) {
        return !player.level().isClientSide();
    }
}
