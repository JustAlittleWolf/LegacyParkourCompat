package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockMovementCollisionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * 1.13/1.13.1/1.13.2 ornithe-feather: BrewingStandBlock.SHAPE/getShape is a union;
 * WorldView.getCollisions/getBlockCollisions excludes the entire block candidate
 * when it overlaps the starting entity box. 1.13.2 getBlockCollisions translates
 * the exclusion box to block-local coordinates before VoxelShapes.overlap(AND).
 * 1.14 ornithe-feather: VoxelShapes.m_84822986 directly clips block collision shapes.
 * 26.2 unobfuscated: BlockCollisions.computeNext/VoxelShape.collide retain that
 * direct clipping architecture. This delta restores only the stand exclusion;
 * non-overlapping candidates continue through the native voxel clipper. The
 * 26.2 BrewingStandBlock.SHAPE/getShape union has identical occupied geometry:
 * the stem below the plate top is redundant, so its raised bottom changes no
 * occupied cells. Reuse that provider rather than duplicate its geometry.
 * The accepted response is conditional on an embedded default standing player;
 * this does not establish natural entry, whole-tick parity or other collectors.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class BrewingStandOverlapExclusion implements BlockMovementCollisionHook {
    @Override public String variant() { return "minecraft:brewing_stand"; }

    @Override
    public VoxelShape collect(Entity player, BlockPos pos, AABB query, VoxelShape nativeShape,
                              List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip) {
        VoxelShape excluded = Shapes.create(player.getBoundingBox()).move(-pos.getX(), -pos.getY(), -pos.getZ());
        return Shapes.joinIsNotEmpty(excluded, nativeShape, BooleanOp.AND) ? Shapes.empty() : nativeShape;
    }
}
