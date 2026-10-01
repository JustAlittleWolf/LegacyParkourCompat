package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Versioned gate for local-player sneak input slowdown. */
@MechanicType("player.input.sneak-slowdown")
public interface SneakInputSlowdownBehavior extends VersionedMechanic {
    boolean shouldSlowDown(Player player);
}
