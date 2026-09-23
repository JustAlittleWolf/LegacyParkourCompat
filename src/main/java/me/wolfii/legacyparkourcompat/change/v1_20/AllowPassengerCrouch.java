package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PassengerCrouchBehavior;
import net.minecraft.world.entity.player.Player;

/** The 1.20.2 passenger exclusion was absent in earlier crouch decisions. */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class AllowPassengerCrouch implements PassengerCrouchBehavior {
    @Override
    public boolean blocksCrouching(Player player, boolean vanilla) {
        return false;
    }
}
