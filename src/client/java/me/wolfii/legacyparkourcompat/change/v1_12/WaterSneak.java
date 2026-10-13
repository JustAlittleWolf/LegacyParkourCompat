package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSneakHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   protected void knockDownwards().
 *   protected void knockDownwards().
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
