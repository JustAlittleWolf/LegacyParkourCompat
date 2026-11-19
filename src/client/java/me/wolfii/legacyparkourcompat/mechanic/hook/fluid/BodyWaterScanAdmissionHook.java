package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

/** Admission for a separately projected body-water column when native padding rejects it. */
@MechanicType("fluid.body-water-scan-admission")
public interface BodyWaterScanAdmissionHook extends VersionedMechanic {
    /** Both Y coordinates are included in the sampled column. */
    boolean admitsColumn(int minY, int maxY);

    /** Surface height used only by an admitted fallback without a separate surface provider. */
    default Optional<Float> fallbackHeight(Level level, BlockPos pos, FluidState state) {
        return Optional.empty();
    }
}
