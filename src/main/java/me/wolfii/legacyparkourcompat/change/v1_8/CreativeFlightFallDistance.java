package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FlightFallDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void moveRelative(float sideways, float forwards) {; lines 1279-1295.
 *   Source SHA-256: e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88.
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void moveRelative(float sideways, float forwards) {; lines 1372-1390.
 *   Source SHA-256: d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85.
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
