package me.wolfii.legacyparkourcompat.change.v1_18_2;

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
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   move; lines 889-894.
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * 1.18.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 543-563.
 *   Source SHA-256: 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isStayingOnGroundSurface; lines 303-305.
 *   maybeBackOffFromEdge; lines 1032-1081.
 *   isAboveGround; lines 1082-1085.
 *   Source SHA-256: bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a.
 * 1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   move; lines 979-984.
 *   Source SHA-256: 36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef.
 * 1.19.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 547-567.
 *   Source SHA-256: 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6.
 * 1.19.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isStayingOnGroundSurface; lines 312-314.
 *   maybeBackOffFromEdge; lines 1061-1114.
 *   isAboveGround; lines 1115-1118.
 *   Source SHA-256: 155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/90e97d96803bd8ab98f1d68181af01e1eaf00a48f2bb58ff13634c8757778c9d.md; accepted, bounded.
 *   Reviewed snapshot ce9369edd2bfe296e56a0a5253a47a5cd486dc3a:workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S2-EDGE.md.
 *   Review 0648b843fecf2358165a7c387cf348b02c66396b; accepted for finding-only handoff.
 *   Reviewed snapshot 1d5f18176eccc5103c08bd1807b2f2c32f2b3376; SHA-256 4a4a23d071397f4cd3a1c82ef4e4ff59885df9b63c79b425c927c310bd1dbbb9.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
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
        if (player.getAbilities().flying
            || (moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !stayingOnGroundSurface
            || !isAboveGround(player, probeDistance)) {
            return delta;
        }

        return SneakEdgeBackoff.apply(player, delta, probeDistance, collisionQuery);
    }

    private static boolean isAboveGround(Player player, float probeDistance) {
        return player.onGround()
            || player.fallDistance < probeDistance
                && !player.level().noCollision(
                    player,
                    player.getBoundingBox().move(0.0D, player.fallDistance - probeDistance, 0.0D)
                );
    }
}
