package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Cached ordinary airborne coefficient, updated after inherited travel and read on the next tick. */
@MechanicType("player.air.speed")
public interface AirSpeedHook extends VersionedMechanic {
    float speed(Player player, float stored, float vanilla);

    float afterAiStep(Player player);
}
