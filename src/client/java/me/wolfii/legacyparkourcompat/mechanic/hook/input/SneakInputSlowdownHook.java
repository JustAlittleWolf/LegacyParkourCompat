package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Versioned gate for local-player sneak input slowdown. */
@FunctionalInterface
@MechanicType("player.input.sneak-slowdown")
public interface SneakInputSlowdownHook extends VersionedMechanic {
    boolean shouldSlowDown(Player player);
}
