package me.wolfii.legacyparkourcompat.mechanic.hook.fishing;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

/** Tracks only the pointer used by the direct client-player retrieval response. */
@MechanicType("fishing.retrieval_target")
public interface FishingRetrievalTargetHook extends VersionedMechanic {
    default @Nullable Entity afterMetadata(@Nullable Entity previous, int synchronizedId) {
        return previous;
    }

    default @Nullable Entity afterTick(@Nullable Entity previous, int synchronizedId, Level level) {
        return previous;
    }

    default @Nullable Entity eventTarget(@Nullable Entity tracked, @Nullable Entity nativeTarget) {
        return nativeTarget;
    }
}
