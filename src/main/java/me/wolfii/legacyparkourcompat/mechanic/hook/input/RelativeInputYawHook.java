package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Resolves the double argument used by the native relative-input trigonometry. */
@MechanicType("player.relative_input_yaw_radians")
public interface RelativeInputYawHook extends VersionedMechanic {
    double toRadians(Player player, float yawDegrees, double vanillaRadians);
}
