package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 693-703.
 *   Source SHA-256: 6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 2115-2140.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   isSprintingPossible; lines 1096-1108.
 *   Source SHA-256: 948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   isInShallowWater; lines 1473-1474.
 *   Source SHA-256: 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3.
 * 1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travelInWater; lines 2380-2382.
 *   Source SHA-256: 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8.
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
