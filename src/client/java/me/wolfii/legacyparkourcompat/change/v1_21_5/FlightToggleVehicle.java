package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightToggleVehicleHook;

/**
 * Ornithe Feather 1.8.9 / 1.12.2 LocalClientPlayerEntity.mobTick and
 * Mojmap 1.16, 1.21.5 and 1.21.10 LocalPlayer.aiStep double-press toggle have
 * no vehicle/jumpableVehicle gate. Mojmap 1.21.11 adds it after !isSwimming,
 * still present in 26.2 unobfuscated LocalPlayer.aiStep. Bypass only the
 * vehicle operand; prior input, auto-jump, seven-tick timer, swimming and
 * spectator checks and ability writes stay native.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class FlightToggleVehicle implements FlightToggleVehicleHook {
    @Override
    public boolean checksVehicle(ParkourVersion selected) {
        return false;
    }
}
