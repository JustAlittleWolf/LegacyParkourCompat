package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;

/** Sequential vertical writes when flight sneak and jump are both held. */
@MechanicType("player.flight.simultaneous_vertical_input")
public interface SimultaneousFlightInputHook extends VersionedMechanic {
    void apply(Player player, ParkourVersion selected, Input keys);
}
