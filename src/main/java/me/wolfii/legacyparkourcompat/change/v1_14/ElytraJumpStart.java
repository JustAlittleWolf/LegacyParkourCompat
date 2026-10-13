package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingStartHook;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines [[692, 711]].
 *   aiStep; lines [[707, 711]].
 *   aiStep; lines [[690, 711]].
 *   Source SHA-256: 0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f.
 * 1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines [[684, 719]].
 *   aiStep; lines [[715, 719]].
 *   aiStep; lines [[695, 719]].
 *   Source SHA-256: 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-campaign-2026-10-07/SNAPSHOT-REVIEW-2026-10-08.md; accepted, bounded.
 *   Reviewed snapshot 105ec98f7b092a8b68e3d93a9739a720fe10e578:workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-ELYTRA-FLIGHT-TOGGLE-ORDER.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/5232d177a412d132df73bd7807c415f751e3babc9396f649a259db0965f60ace.md; accepted, bounded.
 *   Reviewed snapshot 4a0c35f2008d785867c00360a7b72726e3506435:workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-ELYTRA-START.md.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class ElytraJumpStart implements FallFlyingStartHook {
    @Override
    public boolean shouldStartFallFlying(Player player) {
        if (player.onGround()
            || !(player.getDeltaMovement().y < 0.0)
            || player.isFallFlying()
            || player.getAbilities().flying) {
            return false;
        }

        var chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        return chestItem.is(Items.ELYTRA)
            && chestItem.getDamageValue() < chestItem.getMaxDamage() - 1;
    }

    @Override
    public boolean isOnClimbableForFallFlyingStart(Player player, boolean vanilla) {
        return false;
    }

    @Override
    public boolean justToggledCreativeFlightForStart(Player player, boolean vanilla) {
        return false;
    }
}
