package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical look-vector Y component used by the swimming pitch correction. */
@FunctionalInterface
@MechanicType("player.swim.pitch_look")
public interface SwimmingPitchHook extends VersionedMechanic {
    default boolean supports(ParkourVersion selected) {
        return selected.newerThanOrEqual(ParkourVersion.V1_13);
    }

    double lookY(Player player, double vanilla);
}
