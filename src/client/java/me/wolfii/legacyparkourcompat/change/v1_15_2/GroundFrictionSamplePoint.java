package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SupportingBlockPositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   getBlockPosBelowThatAffectsMyMovement.
 * 1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   getBlockPosBelowThatAffectsMyMovement, getBlockSpeedFactor, getBlockJumpFactor.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   onSoulSpeedBlock, travel.
 * 1.20/mojmap/net/minecraft/world/entity/Entity.java:
 *   getBlockPosBelowThatAffectsMyMovement, getOnPos(float), checkSupportingBlock.
 * 1.20.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   getBlockPosBelowThatAffectsMyMovement, getOnPos(float).
 * 1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   onSoulSpeedBlock, travel.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   getBlockSpeedFactor, onSoulSpeedBlock.
 * 26.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   getBlockPosBelowThatAffectsMyMovement, getOnPos(float).
 * Restores the 1.15–1.19.4 position-column sample for all movement-factor consumers. First in 1.20,
 * getOnPos(0.500001F) floors Y for ordinary tracked supports, but returns the tracked position
 * unchanged for fences, walls, and fence gates; with no tracked support it floors entity X/Y/Z.
 * The older calculation always floors entity X/Z and bounding-box minY - 0.5000001. The vanilla
 * water/bubble-column, flying, Soul Speed, and travel gates remain in their original consumers.
 * Native support tracking remains active; this query simply does not consume its result.
 * This is independent of the air/fence/wall/gate fallback in getOnPos(float).
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
// The same operation previously had identical catalog entries for 1.15.2 and 1.19.4.
// Its last applicable profile is 1.19.4; the resolver also selects it for earlier
// profiles above the separate 1.14 delta, without a second registration.
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class GroundFrictionSamplePoint implements SupportingBlockPositionHook {
    @Override
    public BlockPos getPosition(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(
            entity.getX(),
            entity.getBoundingBox().minY - 0.5000001,
            entity.getZ()
        );
    }
}
