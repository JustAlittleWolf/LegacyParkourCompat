package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import net.minecraft.util.Mth;

/**
 * The material scan changes its exclusive upper limit from {@code floor(max + 1.0)}
 * in 1.8.9 to {@code ceil(max)} in 1.9.4. Sources: Ornithe Feather Gen2 build 2,
 * {@code World.containsMaterial} in both releases.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class LavaMaterialScanUpperBound implements LavaMaterialScanUpperBoundHook {
    public LavaMaterialScanUpperBound() {
    }

    @Override
    public int exclusiveUpperBound(double maximum) {
        return Mth.ceil(maximum);
    }
}
