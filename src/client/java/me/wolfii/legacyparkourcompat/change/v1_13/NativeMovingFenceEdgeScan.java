package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovingFenceEdgeScanHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

/** The two-face omission starts in 1.13.1; preserve existing collection for earlier profiles. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class NativeMovingFenceEdgeScan implements MovingFenceEdgeScanHook {
    @Override
    public boolean omitEdge(AABB query, BlockPos pos, BlockEntity blockEntity) {
        return false;
    }
}
