package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Supplies the historical player pose collision-fit result. The accepted
 * producer covers 1.16 through 1.21.4; consumers preserve native fallback
 * outside that range unless another resolved producer applies.
 */
@MechanicType("player.input.pose-fit")
public interface PoseFitHook extends VersionedMechanic {
    boolean canFit(Player player, Pose pose);
}
