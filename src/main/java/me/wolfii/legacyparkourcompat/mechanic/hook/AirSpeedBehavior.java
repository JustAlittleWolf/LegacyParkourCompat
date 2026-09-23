package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical airborne speed stored after the preceding player tick. */
@MechanicType("player.air.speed")
public interface AirSpeedBehavior extends VersionedMechanic {
    float afterAiStep(Player player);

    default float speed(Player player, float stored, float vanilla) {
        return player.getAbilities().flying && !player.isPassenger() ? vanilla : stored;
    }
}
