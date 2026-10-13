package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Water jump impulse applied after input, independently of the vanilla jump calls. */
@FunctionalInterface
@MechanicType("player.jump.water")
public interface WaterJumpHook extends VersionedMechanic {
    void jumpInWater(Player player);
}
