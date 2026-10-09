package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.WaterDepthJumpGate;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.StaleWaterDepthJumpBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class StaleWaterDepthJump implements StaleWaterDepthJumpBehavior {
    @Override
    public boolean shouldJumpFromRetainedWaterDepth(Player player, double retainedDepth) {
        return WaterDepthJumpGate.shouldJump(player, retainedDepth);
    }
}
