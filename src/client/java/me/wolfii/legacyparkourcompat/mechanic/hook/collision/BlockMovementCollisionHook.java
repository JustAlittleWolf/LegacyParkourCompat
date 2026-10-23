package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import java.util.List;

/** Collects one block's candidate for a player movement collision query. */
@MechanicType("block.movement_collision")
public interface BlockMovementCollisionHook extends VersionedMechanic {
    /** Provider parts are ordered block-local shapes, supplied by BlockCollisionShapeHook. */
    VoxelShape collect(Entity player, BlockPos pos, AABB query, VoxelShape nativeShape,
                       List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip);
}
