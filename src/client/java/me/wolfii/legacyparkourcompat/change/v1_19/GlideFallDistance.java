package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.GlideFallDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Accepted source comparisons cover the {@code fallDistance = 1.0F} write in the already-active
 * dry fall-flying movement branch for Ornithe Feather 1.9 through 1.14, Mojmap 1.14.4 through
 * 1.18.2, and 1.19.2. The write occurs only when the incoming vertical delta is strictly greater
 * than {@code -0.5}; that branch has no climbable gate and writes before its glide move.
 * Exact members in the accepted pair archives are Feather
 * {@code net.minecraft.entity.living.LivingEntity.moveRelative(float, float)} with
 * {@code LivingEntity.flyingTick()} before it, Yarn
 * {@code net.minecraft.entity.LivingEntity.travel(Vec3d)} with {@code initAi()} before travel,
 * and Mojmap {@code net.minecraft.world.entity.LivingEntity.travel(Vec3)} with
 * {@code updateFallFlying()} before travel. The Yarn 1.14–1.14.3 members are in the exact Yarn
 * reports; the Mojmap 1.14.4–1.15 boundary is specifically the already-active travel path.
 * Accepted exact-pair comparisons cover the V1_9 through V1_14_2 groups, with the 1.14.4-to-1.15
 * active-travel addendum closing that boundary; accepted Mojmap aliases and later comparisons
 * cover the V1_15 through V1_19 groups. The 1.15.2
 * {@code net.minecraft.client.player.LocalPlayer.tick()} adds a separate {@code !onLadder}
 * startup condition; this provider does not alter that startup path. Mojmap 1.19.3 replaces the
 * direct assignment with {@code Entity.checkSlowFallDistance()} in
 * {@code LivingEntity.travel(Vec3)}.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.9–1.14/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   moveRelative(float, float), flyingTick(), and pre-move fall-distance write.
 * 1.14–1.14.3/yarn/net/minecraft/entity/LivingEntity.java:
 *   travel(Vec3d), initAi(), active-glide branch and pre-move write.
 * 1.14.4–1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel(Vec3), updateFallFlying(), active-glide branch and pre-move write.
 * 1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel, already-active dry Elytra branch and fall-distance write.
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

        boolean climbableHistoricalProfile = selected.newerThanOrEqual(ParkourVersion.V1_9)
            && selected.olderThanOrEqual(ParkourVersion.V1_19);
        if ((climbableHistoricalProfile || !player.onClimbable())
            && player.getDeltaMovement().y > -0.5) {
            player.fallDistance = 1.0F;
        }
    }
}
