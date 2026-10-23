package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import net.minecraft.util.Mth;

/**
 * The material scan uses {@code ceil(max)} from 1.9.4 through 1.13.2;
 * 1.8.9 uses {@code floor(max + 1.0)}. Sources: Ornithe Feather Gen2 build 2,
 * {@code World.containsMaterial} in 1.8.9, 1.9.4, and 1.13.2. This provider is
 * annotated at the latest verified release so it resolves for selected profiles
 * from 1.9 through 1.13.2.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class LavaMaterialScanUpperBound implements LavaMaterialScanUpperBoundHook {
    public LavaMaterialScanUpperBound() {
    }

    @Override
    public int exclusiveUpperBound(double maximum) {
        return Mth.ceil(maximum);
    }
}
