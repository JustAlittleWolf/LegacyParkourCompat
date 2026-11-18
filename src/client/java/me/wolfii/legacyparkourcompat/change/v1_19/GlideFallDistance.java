package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.GlideFallDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.15 through 1.18.2 and 1.19.2
 * {@code net.minecraft.world.entity.LivingEntity.travel(Vec3)} write {@code fallDistance = 1.0F}
 * in the already-active dry fall-flying branch only when the incoming vertical delta is strictly
 * greater than {@code -0.5}; that branch has no climbable gate and writes before its glide move.
 * Accepted Mojmap adjacent aliases cover the V1_15, V1_15_2, V1_16, V1_16_2, V1_17, V1_17_1,
 * and V1_18 profile groups through 1.18.1; the accepted 1.18.2/1.19.2 comparison and 1.19 alias
 * verdicts cover the existing later groups. The 1.15.2
 * {@code net.minecraft.client.player.LocalPlayer.tick()} adds a separate {@code !onLadder}
 * startup condition; this provider does not alter that startup path. Mojmap 1.19.3 replaces the
 * direct assignment with {@code Entity.checkSlowFallDistance()} in
 * {@code LivingEntity.travel(Vec3)}.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15, 1.15.1, 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, 1.16.5, 1.17, 1.17.1,
 * 1.18, 1.18.1, and 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel(Vec3), already-active dry Elytra branch and fall-distance write.
 * 1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   tick(), separate startup {@code !onLadder} condition; not changed by this travel hook.
 * 1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel(Vec3), dry Elytra branch and fall-distance write.
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

        boolean climbableHistoricalProfile = selected.newerThanOrEqual(ParkourVersion.V1_15)
            && selected.olderThanOrEqual(ParkourVersion.V1_19);
        if ((climbableHistoricalProfile || !player.onClimbable())
            && player.getDeltaMovement().y > -0.5) {
            player.fallDistance = 1.0F;
        }
    }
}
