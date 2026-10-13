package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.WaterDepthJumpGate;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.StaleWaterDepthJumpHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   jump input / retained f_85121000 height; lines [[1900, 1918]].
 *   Source SHA-256: bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
 * 1.16/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep fluid-specific jump guard; lines [[2425, 2444]].
 *   Source SHA-256: b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26.
 * Candidate locators only: operation fidelity, independent review and profile applicability remain open.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class StaleWaterDepthJump implements StaleWaterDepthJumpHook {
    @Override
    public boolean shouldJumpFromRetainedWaterDepth(Player player, double retainedDepth, boolean jumpInput) {
        return WaterDepthJumpGate.shouldJump(player, retainedDepth, jumpInput);
    }
}
