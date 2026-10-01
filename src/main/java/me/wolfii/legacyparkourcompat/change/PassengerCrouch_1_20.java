package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PassengerCrouchBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_20)
public final class PassengerCrouch_1_20 implements PassengerCrouchBehavior {
    @Override
    public boolean blocksCrouching(Player player, boolean vanilla) {
        return false;
    }
}
