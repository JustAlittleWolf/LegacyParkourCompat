package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;
import java.util.Optional;

/** Keeps native modifier iteration for versions before the proven identifier change. */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class NativePlayerMovementSpeed implements PlayerMovementSpeedHook {
    @Override
    public Optional<Float> movementSpeed(Snapshot snapshot) {
        return Optional.empty();
    }
}
