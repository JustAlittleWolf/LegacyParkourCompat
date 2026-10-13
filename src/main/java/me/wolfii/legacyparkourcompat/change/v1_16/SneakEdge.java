package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBackoff;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines [[1002, 1049]].
 *   maybeBackOffFromEdge; lines [[1002, 1035]].
 *   Source SHA-256: 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793.
 * 1.16.5/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines [[1010, 1065]].
 *   maybeBackOffFromEdge; lines [[1010, 1060]].
 *   Source SHA-256: d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/90e97d96803bd8ab98f1d68181af01e1eaf00a48f2bb58ff13634c8757778c9d.md; accepted, bounded.
 *   Reviewed snapshot ce9369edd2bfe296e56a0a5253a47a5cd486dc3a:workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S2-EDGE.md.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class SneakEdge implements SneakEdgeHook {
    @Override
    public Vec3 maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryHook collisionQuery
    ) {
        if ((moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !player.onGround()
            || !stayingOnGroundSurface) {
            return delta;
        }

        return SneakEdgeBackoff.apply(player, delta, probeDistance, collisionQuery);
    }
}
