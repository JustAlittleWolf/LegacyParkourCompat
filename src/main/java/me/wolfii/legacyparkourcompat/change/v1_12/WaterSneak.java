package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSneakHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 649-817.
 *   Source SHA-256: 01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc.
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 653-842.
 *   Source SHA-256: 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf.
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   protected void knockDownwards() {; lines 1466-1468.
 *   protected void knockDownwards() {; lines 1466-1468.
 *   Source SHA-256: bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-SNEAK-WATER-DESCENT is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-SNEAK-WATER-DESCENT
 *   Exact independent source verdict/snapshot identity for sneak-water-descent
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterSneak implements WaterSneakHook {
    @Override
    public boolean shouldApplyDownwardImpulse(Player player, boolean vanilla) {
        return false;
    }
}
