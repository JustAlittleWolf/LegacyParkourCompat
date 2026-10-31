package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.bed.BedWakePositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.CollisionGetter;
/**
 * Mojmap 1.15.2 BedBlock.findStandUpPosition is retained natively; the paired
 * 1.16.1 Mojmap BedBlock.findStandUpPosition/getStandingLocationAtOrBelow
 * delta begins at V1_16, so this identity hook preserves earlier behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class BedWakePositionBoundary implements BedWakePositionHook {
    public BedWakePositionBoundary() {
    }

    @Override
    public BedWakePositionHook.Result findStandUpPosition(
        Entity entity,
        EntityType<?> entityType,
        CollisionGetter level,
        BlockPos bedPosition
    ) {
        return BedWakePositionHook.Result.unsupported();
    }
}
