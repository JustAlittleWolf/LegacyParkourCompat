package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedUpdateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep; lines [[504, 510]].
 *   Source SHA-256: 724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep; lines [[506, 512]].
 *   Source SHA-256: bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-012-sprint-air-control-float.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-F012-independent-boundary-review.md; ACCEPT, sampled source boundary.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class DoubleSprintAirSpeed implements AirSpeedHook, AirSpeedUpdateHook {
    @Override
    public float speed(Player player, float stored, float vanilla) {
        return player.getAbilities().flying && !player.isPassenger() ? vanilla : stored;
    }

    @Override
    public float afterAiStep(Player player) {
        float speed = 0.02F;
        if (player.isSprinting()) {
            speed = (float) (speed + 0.005999999865889549);
        }
        return speed;
    }
}
