package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/**
 * Historical entity-entity velocity push involving the player.
 *
 * <p>1.8 ran {@code LivingEntity#tickCramming} only on the server. 1.9 started
 * pushing on the client as well.
 */
@MechanicType("player.entity_push")
public interface EntityPushBehavior extends VersionedMechanic {
    /**
     * {@code false} skips vanilla {@code Entity#push(Entity)} when this player
     * is either side of the pair.
     */
    boolean allow(Entity player, Entity self, Entity other);
}
