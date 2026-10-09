package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.StaleWaterDepthJumpBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_16)
public final class StaleWaterDepthJumpProtection implements StaleWaterDepthJumpBehavior {
    @Override
    public boolean shouldJumpFromRetainedWaterDepth(Player player, double retainedDepth) {
        return false;
    }
}
