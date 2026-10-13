package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeProbeHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 711.
 *   Source SHA-256: 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3.
 * 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines 1067-1117.
 *   canFallAtLeast; lines 1122-1126.
 *   Source SHA-256: 785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2.
 * 1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines 889-939.
 *   canFallAtLeast; lines 944-951.
 *   Source SHA-256: 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81.
 * 1.21.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines 1069-1117.
 *   isAboveGround; lines 1120-1126.
 *   Source SHA-256: c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1.
 * 1.21.5/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines 1026-1074.
 *   isAboveGround; lines 1077-1085.
 *   Source SHA-256: 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036.
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
