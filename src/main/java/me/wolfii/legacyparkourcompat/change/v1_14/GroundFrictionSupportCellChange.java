package me.wolfii.legacyparkourcompat.change.v1_14;

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
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/5232d177a412d132df73bd7807c415f751e3babc9396f649a259db0965f60ace.md; accepted, bounded.
 *   Reviewed snapshot 4a0c35f2008d785867c00360a7b72726e3506435:workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-FRICTION-SAMPLE.md.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class GroundFrictionSupportCellChange implements GroundFrictionBlockHook {
    @Override
    public BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(
            entity.getX(),
            entity.getBoundingBox().minY - 1.0,
            entity.getZ()
        );
    }
}
