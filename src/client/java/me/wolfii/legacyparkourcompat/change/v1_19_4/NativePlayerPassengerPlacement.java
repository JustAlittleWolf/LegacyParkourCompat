package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/** Native lower resolver fence before the accepted 1.20.1 passenger-placement change. */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class NativePlayerPassengerPlacement implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return Optional.empty();
    }
}
