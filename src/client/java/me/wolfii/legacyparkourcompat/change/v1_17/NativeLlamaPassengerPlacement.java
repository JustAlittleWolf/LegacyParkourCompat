package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/** Keeps native passenger placement for the 1.17 profile and earlier unsupported profiles. */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class NativeLlamaPassengerPlacement implements PlayerPassengerPlacementHook {
    public NativeLlamaPassengerPlacement() {
    }

    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return Optional.empty();
    }
}
