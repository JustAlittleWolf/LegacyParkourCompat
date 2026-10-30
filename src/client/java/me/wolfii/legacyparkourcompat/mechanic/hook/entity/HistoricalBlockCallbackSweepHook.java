package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import net.minecraft.world.entity.Entity;

import java.util.List;

/** Executes the selected profile's complete post-travel block callback sweep and finalizer. */
@MechanicType("entity.historical_block_callback_sweep")
public interface HistoricalBlockCallbackSweepHook extends VersionedMechanic {
    default boolean usesHistoricalSweep() {
        return true;
    }

    /** Side-effect-free whole-batch gate; false keeps the complete native traversal intact. */
    default boolean supportsBatch(Entity entity, List<?> movements) {
        return false;
    }

    void apply(Entity entity, List<?> movements);
}
