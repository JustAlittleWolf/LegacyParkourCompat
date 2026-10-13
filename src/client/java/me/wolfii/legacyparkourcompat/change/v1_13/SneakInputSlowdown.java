package me.wolfii.legacyparkourcompat.change.v1_13;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SneakInputSlowdownHook;
import net.minecraft.world.entity.player.Player;
/**
 * KeyboardInput.tick uses the direct sneak key before the 1.14 pose gate.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   tick.
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   mobTick.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   tick.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SneakInputSlowdown implements SneakInputSlowdownHook {
    @Override public boolean shouldSlowDown(Player player) { return player.isShiftKeyDown(); }
}
