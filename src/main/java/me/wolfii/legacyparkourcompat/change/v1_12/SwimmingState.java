package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SwimmingHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public boolean checkWaterCollisions() {; lines 948-964.
 *   Source SHA-256: 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a.
 * 1.13.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public void updateSwimming() {; lines 953-959.
 *   public boolean isSubmergedInWater() {; lines 943-945.
 *   private void m_03231680() {; lines 947-951.
 *   public boolean m_69693160(Tag&lt;Fluid&gt; tag) {; lines 2509-2571.
 *   Source SHA-256: 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269.
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void updateSwimming() {; lines 1471-1477.
 *   Source SHA-256: 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633.
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
