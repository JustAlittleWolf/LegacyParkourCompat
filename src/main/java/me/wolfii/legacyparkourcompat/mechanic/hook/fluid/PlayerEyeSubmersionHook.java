package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

/** Resolves the historical predicate for the player's fluid state at its eye block. */
@MechanicType("fluid.player_eye_submersion")
public interface PlayerEyeSubmersionHook extends VersionedMechanic {
    /** Returns a historical answer, or empty to retain the native eye-surface comparison. */
    Optional<Boolean> isSubmerged(
        FluidState eyeFluidState,
        boolean matchesFluidTag,
        boolean mountedInBoat,
        double eyeY,
        int eyeBlockY
    );
}
