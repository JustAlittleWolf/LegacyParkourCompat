package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpHook;
import net.minecraft.world.entity.player.Player;

/**
 * Auto-jump did not exist in the 1.9 movement profile.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 620-783.
 *   protected void autoJump(float dx, float dz) {; lines 823-923.
 *   Source SHA-256: a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 604-760.
 *   Source SHA-256: 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class NoAutoJump implements AutoJumpHook {
    @Override
    public boolean isAutoJumpEnabled(Player player, boolean vanilla) {
        return false;
    }
}
