package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SupportingBlockPositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/**
 * The support sample remains bounding-box minY minus 1.0 through 1.14.4.
 * 1.14 ornithe-feather LivingEntity#moveRelative and 1.14.1, 1.14.2, 1.14.3 yarn
 * LivingEntity#travel retain this sample across the 1.14.2 alias boundary.
 * 1.15 mojmap Entity#getBlockPosBelowThatAffectsMyMovement instead subtracts
 * 0.5000001 from bounding-box minY.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel samples (x, bounding-box minY - 1.0, z) for ground friction.
 * 1.15/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel uses getBlockPosBelowThatAffectsMyMovement for ground friction.
 * 1.15.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel retains that ground-friction sample.
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   getBlockPosBelowThatAffectsMyMovement.
 * 1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class GroundFrictionSupportCellChange implements SupportingBlockPositionHook {
    @Override
    public BlockPos getPosition(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(
            entity.getX(),
            entity.getBoundingBox().minY - 1.0,
            entity.getZ()
        );
    }
}
