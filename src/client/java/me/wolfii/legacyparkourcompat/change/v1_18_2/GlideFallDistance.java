package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.GlideFallDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.19.2/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization.
 * 1.19.2/mojmap/net/minecraft/world/level/block/FarmBlock.java:
 *   fallOn.
 * 1.19.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkSlowFallDistance.
 * 1.19.3/mojmap/net/minecraft/world/level/block/FarmBlock.java:
 *   fallOn.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkSlowFallDistance.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class GlideFallDistance implements GlideFallDistanceHook {
    @Override
    public void beforeFallFlyingTravel(Player player, ParkourVersion selected) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return;
        }

        if (!player.onClimbable() && player.getDeltaMovement().y > -0.5) {
            player.fallDistance = 1.0F;
        }
    }
}
