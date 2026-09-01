package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/**
 * Historical auto-jump ({@code LocalPlayer#isAutoJumpEnabled}). Auto-jump was
 * added in 1.10; versions before that must keep it off.
 */
@MechanicType("player.auto_jump")
public interface AutoJumpBehavior extends VersionedMechanic {
    boolean isAutoJumpEnabled(Player player, boolean vanilla);
}
