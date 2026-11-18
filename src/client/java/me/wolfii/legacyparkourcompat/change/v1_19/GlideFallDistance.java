package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.GlideFallDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.18.2 and 1.19.2
 * {@code net.minecraft.world.entity.LivingEntity.travel(Vec3)} write {@code fallDistance = 1.0F}
 * in the dry fall-flying branch only when the current vertical delta is strictly greater than
 * {@code -0.5}; that branch has no climbable gate and writes before its glide move. Accepted
 * Mojmap 1.19/1.19.1 and 1.19.1/1.19.2 alias comparisons establish that expression for the whole
 * {@link ParkourVersion#V1_19} profile group. Mojmap 1.19.3 replaces the direct assignment with
 * {@code Entity.checkSlowFallDistance()} in {@code LivingEntity.travel(Vec3)}.
 *
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
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class GlideFallDistance implements GlideFallDistanceHook {
    @Override
    public void beforeFallFlyingTravel(Player player, ParkourVersion selected) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return;
        }

        boolean climbableHistoricalProfile = selected == ParkourVersion.V1_18_2
            || selected == ParkourVersion.V1_19;
        if ((climbableHistoricalProfile || !player.onClimbable())
            && player.getDeltaMovement().y > -0.5) {
            player.fallDistance = 1.0F;
        }
    }
}
