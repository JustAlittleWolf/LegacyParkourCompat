package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Trigonometric lookup used to reconstruct the auto-jump fallback direction. */
@MechanicType("player.auto_jump.fallback_trigonometry")
public interface AutoJumpFallbackTrigHook extends VersionedMechanic {
    float sin(float radians);

    float cos(float radians);
}
