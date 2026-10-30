package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

/** Historical admission of a resident moving-fence candidate at the old scan envelope's faces. */
@MechanicType("collision.moving_fence_edge_scan")
public interface MovingFenceEdgeScanHook extends VersionedMechanic {
    boolean omitEdge(AABB query, BlockPos pos, BlockEntity blockEntity);
}
