package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeProbeHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 * 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 *   canFallAtLeast.
 * 1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 *   canFallAtLeast.
 * 1.21.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 *   isAboveGround.
 * 1.21.5/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 *   isAboveGround.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class EdgeBackoffProbeBounds implements SneakEdgeProbeHook {
    @Override
    public boolean canFallAtLeast(Player player, double deltaX, double deltaZ, double minHeight) {
        AABB boundingBox = player.getBoundingBox();
        AABB probe = new AABB(
            boundingBox.minX + deltaX,
            boundingBox.minY - (float) minHeight - 1.0E-5F,
            boundingBox.minZ + deltaZ,
            boundingBox.maxX + deltaX,
            boundingBox.minY,
            boundingBox.maxZ + deltaZ
        );
        return player.level().noCollision(player, probe);
    }
}
