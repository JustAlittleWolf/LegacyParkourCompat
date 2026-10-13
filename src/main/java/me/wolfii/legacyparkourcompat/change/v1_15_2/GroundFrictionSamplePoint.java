package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.GroundFrictionBlockHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines [[1837, 1842]].
 *   Source SHA-256: 428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681.
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   getBlockPosBelowThatAffectsMyMovement; lines [[600, 602]].
 *   Source SHA-256: 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
 * 1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines [[1886, 1891]].
 *   Source SHA-256: 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 585-599.
 *   Source SHA-256: 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.
 * 1.20.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 629-643.
 *   setOnGround; lines 553-585.
 *   Source SHA-256: 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   tick; lines 227-231.
 *   tick; lines 228-232.
 *   Source SHA-256: 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac.
 * 1.20.1/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   findSupportingBlock; lines 95-110.
 *   Source SHA-256: 8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/5232d177a412d132df73bd7807c415f751e3babc9396f649a259db0965f60ace.md; accepted, bounded.
 *   Reviewed snapshot 4a0c35f2008d785867c00360a7b72726e3506435:workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-FRICTION-SAMPLE.md.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class GroundFrictionSamplePoint implements GroundFrictionBlockHook {
    @Override
    public BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(
            entity.getX(),
            entity.getBoundingBox().minY - 0.5000001,
            entity.getZ()
        );
    }
}
