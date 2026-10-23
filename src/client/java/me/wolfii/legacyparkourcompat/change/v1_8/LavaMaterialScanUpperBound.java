package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import net.minecraft.util.Mth;

/**
 * The material scan uses {@code floor(max + 1.0)} as its exclusive limit in
 * 1.8.9 (Ornithe Feather Gen2 build 2, {@code World.containsMaterial}).
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LavaMaterialScanUpperBound implements LavaMaterialScanUpperBoundHook {
    public LavaMaterialScanUpperBound() {
    }

    @Override
    public int exclusiveUpperBound(double maximum) {
        return Mth.floor(maximum + 1.0);
    }
}
