package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/** Controls whether vanilla's Player bubble-column callback response is eligible to run. */
@MechanicType("player.bubble_column_callback")
public interface BubbleColumnPlayerCallbackHook extends VersionedMechanic {
    /**
     * Returns an override policy for the callback. {@link Optional#of(Object)} with
     * {@code true} requires forcing the inherited Entity callback for this Player,
     * including when its current flying guard would skip the native response.
     * {@link Optional#empty()} leaves the current native callback behavior unchanged.
     */
    Optional<Boolean> forceLegacyCallback(Player player);
}
