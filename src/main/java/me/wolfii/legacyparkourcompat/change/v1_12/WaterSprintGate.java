package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSprintGateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 649-817.
 *   Source SHA-256: 01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc.
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 653-842.
 *   Source SHA-256: 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf.
 * Bounded source decisions:
 *   Review 92bf9c5b944f46849b72042b02480125e6d37435; ACCEPT, bounded local-player sprint-state rules.
 *   Reviewed snapshot 3a60fe735560e478bf0aa0d05f5e306c74800f6a; SHA-256 8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114.
 *   Review 92bf9c5b944f46849b72042b02480125e6d37435; ACCEPT, bounded local-player sprint-state rules.
 *   Reviewed snapshot 3a60fe735560e478bf0aa0d05f5e306c74800f6a; SHA-256 8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-WATER-SPRINT is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for water-sprint-gates
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterSprintGate implements WaterSprintGateHook {
    @Override
    public boolean allowShallowWaterSprint(Player player, boolean vanilla) {
        return true;
    }
}
