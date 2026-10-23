package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Resolves the radians value used by the native relative-input trigonometry. */
@MechanicType("player.relative_input_yaw_radians")
public interface RelativeInputYawHook extends VersionedMechanic {
    float toRadians(Player player, float yawDegrees, float vanillaRadians);
}
