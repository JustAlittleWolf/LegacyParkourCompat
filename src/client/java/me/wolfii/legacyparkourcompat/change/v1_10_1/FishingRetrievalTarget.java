package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fishing.FishingRetrievalTargetHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

/**
 * 1.10, 1.10.1 and 1.10.2, Ornithe Feather Gen2 build 2:
 * FishingBobberEntity.onDataValueChanged clears an existing pointer only for
 * positive HOOKED_ENTITY; tick resolves a positive ID after super.tick, then
 * clears a removed target. doEvent(31) reads that pointer for its local-player gate.
 * 1.11 (and 1.11.1/1.11.2), same mappings, onDataValueChanged resolves immediately.
 * Native 26.2 unobfuscated FishingHook.onSyncedDataUpdated resolves immediately;
 * handleEntityEvent reads hookedIn. This sidecar changes only that response gate.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class FishingRetrievalTarget implements FishingRetrievalTargetHook {
    @Override
    public @Nullable Entity afterMetadata(@Nullable Entity previous, int synchronizedId) {
        return synchronizedId > 0 ? null : previous;
    }

    @Override
    public @Nullable Entity afterTick(@Nullable Entity previous, int synchronizedId, Level level) {
        Entity target = previous;
        if (synchronizedId > 0 && target == null) {
            target = level.getEntity(synchronizedId - 1);
        }
        return target != null && target.isRemoved() ? null : target;
    }

    @Override
    public @Nullable Entity eventTarget(@Nullable Entity tracked, @Nullable Entity nativeTarget) {
        return tracked;
    }
}
