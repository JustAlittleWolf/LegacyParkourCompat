package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/** Native resolver fence before the exact 1.17 Llama rider-height provider. */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class NativePlayerPassengerPlacement implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return Optional.empty();
    }
}
