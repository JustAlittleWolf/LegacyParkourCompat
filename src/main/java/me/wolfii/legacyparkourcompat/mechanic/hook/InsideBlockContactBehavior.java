package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical inset used when enumerating blocks intersected by player movement. */
@FunctionalInterface
@MechanicType("player.inside_block_contact")
public interface InsideBlockContactBehavior extends VersionedMechanic {
    double inset(Player player, double vanilla);
}
