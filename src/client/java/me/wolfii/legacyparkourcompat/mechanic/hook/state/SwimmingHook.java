package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Swimming state queried for player movement. */
@FunctionalInterface
@MechanicType("player.swim")
public interface SwimmingHook extends VersionedMechanic {
    boolean isSwimming(Player player, boolean vanilla);
}
