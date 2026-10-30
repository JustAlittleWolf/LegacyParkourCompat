package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.HistoricalBlockCallbackSweepHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.Movement;

import java.util.List;

/** Keeps native callback traversal and finalization for profiles before 1.21.2. */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class HistoricalBlockCallbackSweepNativeFence implements HistoricalBlockCallbackSweepHook {
    @Override
    public boolean usesHistoricalSweep() {
        return false;
    }

    @Override
    public boolean supportsBatch(Entity entity, List<Movement> movements) {
        return false;
    }

    @Override
    public void apply(Entity entity, List<Movement> movements) {
        throw new UnsupportedOperationException("Native callback sweep must remain on the native path");
    }
}
