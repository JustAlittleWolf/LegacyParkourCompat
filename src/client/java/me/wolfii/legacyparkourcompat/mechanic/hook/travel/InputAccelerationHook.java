package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Applies relative input acceleration; false retains the native operation. */
@MechanicType("player.travel.input_acceleration")
public interface InputAccelerationHook extends VersionedMechanic {
    boolean apply(Player player, ParkourVersion selected, float speed, Vec3 input);
}
