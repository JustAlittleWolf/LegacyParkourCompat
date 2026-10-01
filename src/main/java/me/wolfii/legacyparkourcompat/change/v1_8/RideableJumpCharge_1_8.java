package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.RideableJumpBehavior;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class RideableJumpCharge_1_8 implements RideableJumpBehavior {
    @Override
    public void onPlayerJump(Player player, PlayerRideableJumping mount, int strength, VanillaCall vanilla) {
        // 1.8 sends the charge packet without applying the charge to the local mount.
        if (!(mount instanceof net.minecraft.world.entity.Entity entity)) {
            throw new IllegalStateException("Rideable jumping mount is not an entity");
        }
        if (!me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(entity, ParkourVersion.V1_8)) {
            vanilla.run();
        }
    }
}
