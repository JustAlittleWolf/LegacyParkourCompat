package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SprintCollisionHook;
import net.minecraft.world.entity.player.Player;

/**
 * Restores stopping sprint on every horizontal collision.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep, isHorizontalCollisionMinor.
 *   aiStep.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class SprintCollision implements SprintCollisionHook {
    @Override
    public boolean shouldStopRunSprinting(Player player, boolean vanilla) {
        return vanilla || player.horizontalCollision;
    }
}
