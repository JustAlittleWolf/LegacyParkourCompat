package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;

import java.util.Optional;

/** Retains native modifier order until the 1.20.2 HashSet boundary. */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class NativePlayerMovementSpeed implements PlayerMovementSpeedHook {
    @Override
    public Optional<Float> movementSpeed(Snapshot snapshot) {
        return Optional.empty();
    }
}
