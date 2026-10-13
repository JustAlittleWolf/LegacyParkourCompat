package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   isSprintingPossible.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   isInShallowWater.
 * 1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travelInWater.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class ShallowWaterSprintEligibility implements ShallowWaterSprintHook {
    @Override
    public boolean isInShallowWaterForSprintEligibility(
        Player player,
        boolean sprintKeyDown,
        boolean sprinting,
        boolean vanilla
    ) {
        return (sprinting || sprintKeyDown) && vanilla;
    }
}
