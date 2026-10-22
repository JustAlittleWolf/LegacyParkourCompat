package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.PowderSnowInsideCollisionShapeHook;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Mojmap 1.21.4 BlockBehaviour#getEntityInsideCollisionShape returns Shapes.block;
 * Entity#checkInsideBlocks invokes PowderSnowBlock#entityInside for that sentinel.
 * Mojmap 1.21.5 PowderSnowBlock#getEntityInsideCollisionShape instead uses its
 * entity-specific collision shape, with a full-block fallback only when empty.
 * The falling player's 0.9-high shape can therefore suppress the inside callback.
 * Published 26.2 PowderSnowBlock#getEntityInsideCollisionShape retains this provider.
 *
 * Applicability: Mojmap 1.21.2 and 1.21.3 inherit the same full-block provider;
 * every exact release from 1.17 through 1.21.1 has no powder-snow inside-shape
 * override, and Entity#checkInsideBlocks invokes the block callback directly.
 * This restores only the powder-snow shape decision within the existing traversal.
 * Powder snow was introduced in 1.17; earlier profiles retain its native behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PowderSnowInsideCollisionShape implements PowderSnowInsideCollisionShapeHook {
    @Override
    public VoxelShape entityInsideCollisionShape(ParkourVersion selected, VoxelShape vanilla) {
        return selected.olderThan(ParkourVersion.V1_17) ? vanilla : Shapes.block();
    }
}
