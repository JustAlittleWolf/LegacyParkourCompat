package me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Selects whether a movement contributes a block-callback segment record. */
@MechanicType("block_callback.record_admission")
public interface MovementCallbackRecordHook extends VersionedMechanic {
    boolean suppressMovementRecord(Entity entity);

    /** Whether move captures the source's axis-split batch for this entity. */
    default boolean captureAxisSegments(Entity entity) {
        return false;
    }
}
