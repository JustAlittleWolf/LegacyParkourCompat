package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical look-vector Y component used by the swimming pitch correction. */
@FunctionalInterface
@MechanicType("player.swim.pitch_look")
public interface SwimmingPitchBehavior extends VersionedMechanic {
    double lookY(Player player, double vanilla);
}
