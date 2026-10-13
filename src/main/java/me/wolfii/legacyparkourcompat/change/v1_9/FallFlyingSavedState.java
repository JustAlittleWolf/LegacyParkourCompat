package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FallFlyingSavedStateHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void writeCustomNbt(NbtCompound nbt) {; lines 427-461.
 *   public void readCustomNbt(NbtCompound nbt) {; lines 464-497.
 *   Source SHA-256: d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82.
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void writeCustomNbt(NbtCompound nbt) {; lines 425-457.
 *   public void readCustomNbt(NbtCompound nbt) {; lines 460-489.
 *   Source SHA-256: bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5.
 * Bounded source decisions:
 *   Review a02e52db9d6212449610caefa5a2a630736a2447; accepted.
 *   Reviewed snapshot 4bded1c9cda9fd3dae61675d6f3083956806b8af; SHA-256 3e55452cc24c5684544afc3c6f17af2fe06ad2712b2512c8399d6cdff89b5a4b.
 *   Review a02e52db9d6212449610caefa5a2a630736a2447; accepted.
 *   Reviewed snapshot 4bded1c9cda9fd3dae61675d6f3083956806b8af; SHA-256 3e55452cc24c5684544afc3c6f17af2fe06ad2712b2512c8399d6cdff89b5a4b.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-campaign-2026-10-07/SNAPSHOT-REVIEW-POSE-FALLFLY-2026-10-08.md; [{&#x27;section&#x27;: &#x27;F005 — ACCEPT&#x27;, &#x27;verdict&#x27;: [], &#x27;snapshot_hash_matches&#x27;: True}].
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F005-fall-flying-state-persists-across-player-load is unknown within (1.9.4, 1.10.2]
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class FallFlyingSavedState implements FallFlyingSavedStateHook {
    @Override
    public boolean shouldPersistFallFlyingState(Player player, ParkourVersion selected) {
        return selected != ParkourVersion.V1_9;
    }

    @Override
    public void afterFallFlyingStateLoaded(Player player, ParkourVersion selected) {
        if (selected == ParkourVersion.V1_9) {
            ((EntityInvoker) player).legacyparkourcompat$setSharedFlag(7, false);
        }
    }
}
