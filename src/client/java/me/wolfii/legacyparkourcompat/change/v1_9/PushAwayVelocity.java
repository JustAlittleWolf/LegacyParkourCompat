package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PushAwayVelocityHook;
import net.minecraft.world.entity.player.Player;

/**
 * Retains the float literal widened into the selected double velocity component
 * through 1.13.2. Escape occupancy and probe coordinates are separate operations.
 * 1.13.2, ornithe-feather: LocalClientPlayerEntity.pushAwayFrom assigns +/-0.1F.
 * 1.14, ornithe-feather: LocalClientPlayerEntity.pushAwayFrom instead passes
 * +/-0.1D to Entity.m_32166403, which directly constructs the velocity vector.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class PushAwayVelocity implements PushAwayVelocityHook {
    @Override
    public double pushAwayVelocity(Player player, ParkourVersion target, double vanilla) {
        // The float is widened when assigned to the double velocity fields.
        float g = 0.1F;
        return vanilla < 0.0D ? -g : g;
    }
}
