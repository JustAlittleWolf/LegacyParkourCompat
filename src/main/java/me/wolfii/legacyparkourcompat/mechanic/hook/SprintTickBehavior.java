package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Callback at the start of the local player AI tick. */
@FunctionalInterface
@MechanicType("player.sprint.tick")
public interface SprintTickBehavior extends VersionedMechanic {
    void tick(Player player);
}
