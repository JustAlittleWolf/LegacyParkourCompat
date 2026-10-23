package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBorderCollision;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   getBlockCollisions.
 *   getBlockCollisions.
 * 1.16.5/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   noCollision.
 *   getBlockCollisions.
 * 1.16.5/mojmap/net/minecraft/world/level/CollisionSpliterator.java:
 *   getChunk.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class SneakEdgeCollisionQuery implements SneakEdgeCollisionQueryHook {
    @Override
    public boolean noCollision(Player player, AABB candidate) {
        if (SneakEdgeBorderCollision.hasCollision(player)) {
            return false;
        }

        return player.level().noBlockCollision(player, candidate)
            && player.level().noEntityCollision(player, candidate);
    }
}
