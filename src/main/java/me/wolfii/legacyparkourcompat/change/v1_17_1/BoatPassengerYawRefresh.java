package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerYawRefreshHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.Boat;

/**
 * Restores the 1.17.1 movement-facing yaw write on a passenger-list refresh.
 * Previous-yaw interpolation and head-yaw rendering fields are intentionally excluded.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   heal; lines [[154, 169]].
 *   Source SHA-256: c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   startRiding; lines [[153, 163]].
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/90e97d96803bd8ab98f1d68181af01e1eaf00a48f2bb58ff13634c8757778c9d.md; accepted, bounded.
 *   Reviewed snapshot 064fc24e5e3d8b4dab4f12c2000dcb13d37514ca:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-006-boat-passenger-yaw-refresh.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-006-boat-passenger-yaw-refresh.md.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class BoatPassengerYawRefresh implements BoatPassengerYawRefreshHook {
    @Override
    public void onPassengerRefresh(Player player, Boat boat, ParkourVersion selected) {
        if (selected == ParkourVersion.V1_17_1 && boat.hasIndirectPassenger(player)) {
            player.setYRot(boat.getYRot());
        }
    }
}
