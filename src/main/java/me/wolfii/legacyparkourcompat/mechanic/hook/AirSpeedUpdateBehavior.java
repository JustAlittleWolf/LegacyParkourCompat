package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Air speed computed after the preceding player travel tick. */
@FunctionalInterface
@MechanicType("player.air.speed.update")
public interface AirSpeedUpdateBehavior extends VersionedMechanic {
    float afterAiStep(Player player);
}
