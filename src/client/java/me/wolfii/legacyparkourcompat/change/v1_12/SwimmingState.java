package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SwimmingHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public boolean checkWaterCollisions().
 * 1.13.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public void updateSwimming().
 *   public boolean isSubmergedInWater().
 *   private void m_03231680().
 *   public boolean m_69693160(Tag&lt;Fluid&gt; tag).
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void updateSwimming().
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-WATER-STATE is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-WATER-STATE
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class SwimmingState implements SwimmingHook {
    @Override
    public boolean isSwimming(Player player, boolean vanilla) {
        return false;
    }
}
