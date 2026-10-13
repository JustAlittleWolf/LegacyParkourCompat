package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FlightFallDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void moveRelative(float sideways, float forwards).
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void moveRelative(float sideways, float forwards).
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of legacy-run-MD-06-creative-flight-resets-fall-distance is unknown within (1.8.9, 1.9.4]
 *   Exact independent source verdict/snapshot identity for MD-06-creative-flight-resets-fall-distance
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class CreativeFlightFallDistance implements FlightFallDistanceHook {
    @Override
    public void beforeMovement(Player player, VanillaCall vanilla) {
        if (!player.getAbilities().flying || player.isPassenger()) {
            vanilla.run();
        }
        // Version 1.8 preserves fall distance through creative-flight movement.
    }
}
