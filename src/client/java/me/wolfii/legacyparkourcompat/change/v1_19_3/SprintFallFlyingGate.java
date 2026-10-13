package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintFallFlyingGateHook;
import net.minecraft.world.entity.player.Player;

/**
 * The held-key sprint branch has no fall-flying veto through 1.19.3; 1.19.4
 * introduces that veto in canStartSprinting. This masks the later native gate.
 * 1.19/mojmap/net/minecraft/client/player/LocalPlayer.java: aiStep.
 * 1.19.1/mojmap/net/minecraft/client/player/LocalPlayer.java: aiStep.
 * 1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java: aiStep.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   updateFallFlying.
 *   travel.
 *   getFluidFallingAdjustedMovement.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   setSprinting.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   updateFallFlying.
 *   travel.
 *   getFluidFallingAdjustedMovement.
 *   setSprinting.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   tryToStartFallFlying.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java. Archived source identity correspondence remains unresolved for them.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class SprintFallFlyingGate implements SprintFallFlyingGateHook {
    @Override
    public boolean fallFlyingForSprintGate(Player player, ParkourVersion selected, boolean vanilla) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return vanilla;
        }

        return false;
    }
}
