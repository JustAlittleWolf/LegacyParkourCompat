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
 *   tick; lines [[13, 50]].
 *   Source SHA-256: 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a.
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   mobTick; lines [[701, 740]].
 *   Source SHA-256: 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   tick; lines [[13, 26]].
 *   Source SHA-256: 5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SneakInputSlowdown implements SneakInputSlowdownHook {
    @Override public boolean shouldSlowDown(Player player) { return player.isShiftKeyDown(); }
}
