package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("player.trapdoor_climbing")
public interface TrapdoorClimbingHook extends VersionedMechanic {
    boolean canClimbTrapdoor(boolean vanillaResult);
}
