package me.wolfii.legacyparkourcompat.mechanic.hook.gliding;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Decides the stage at which the untouched native glide fall-distance limiter runs. */
@MechanicType("player.glide.fall_distance_limiter_stage")
public interface GlideFallDistanceLimiterStageHook extends VersionedMechanic {
    /**
     * The callback performs only the native limiter, never travel or fall-flying maintenance.
     * Native placement runs at UPDATE_FALL_FLYING and does nothing at TRAVEL_FALL_FLYING.
     */
    void atStage(LivingEntity entity, ParkourVersion selectedVersion, Stage stage, VanillaCall nativeLimiter);

    enum Stage {
        UPDATE_FALL_FLYING,
        TRAVEL_FALL_FLYING
    }
}
