package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Water jump impulse when a modern shallow-fluid path selects a ground jump. */
@MechanicType("player.jump.water")
public interface WaterJumpBehavior extends VersionedMechanic {
    void jumpInWater(Player player);
}
