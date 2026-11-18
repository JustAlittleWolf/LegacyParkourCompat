package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Policy for the shared fall-flying flag after local ability-flight travel. */
@MechanicType("player.ability_flight_glide_flag")
public interface AbilityFlightGlideFlagHook extends VersionedMechanic {
    boolean shouldClearAfterTravel(Player player);
}
