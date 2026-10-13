package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether local-player auto-jump is enabled. */
@FunctionalInterface
@MechanicType("player.auto_jump")
public interface AutoJumpHook extends VersionedMechanic {
    boolean isAutoJumpEnabled(Player player, boolean vanilla);
}
