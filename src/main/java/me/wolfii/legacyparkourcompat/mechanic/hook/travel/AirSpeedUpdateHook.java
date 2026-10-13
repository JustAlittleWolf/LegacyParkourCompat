package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Air speed computed after the preceding player travel tick. */
@FunctionalInterface
@MechanicType("player.air.speed.update")
public interface AirSpeedUpdateHook extends VersionedMechanic {
    float afterAiStep(Player player);
}
