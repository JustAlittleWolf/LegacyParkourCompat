package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundJumpGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LiquidJumpGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.StaleWaterDepthJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterJumpBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;

@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterJump implements WaterJumpBehavior, GroundJumpGateBehavior, LiquidJumpGateBehavior, StaleWaterDepthJumpBehavior {
    @Override
    public void jumpInWater(Player player) {
        if (player.isInWater()) {
            player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.04F, 0.0));
        }
    }

    @Override
    public boolean shouldJumpFromGround(Player player, boolean vanilla) {
        return !player.isInWater() && vanilla;
    }

    @Override
    public boolean shouldJumpInLiquid(Player player, TagKey<Fluid> fluid, boolean vanilla) {
        return !(fluid == FluidTags.WATER && player.isInWater()) && vanilla;
    }

    @Override
    public boolean shouldJumpFromRetainedWaterDepth(Player player, double retainedDepth) {
        return false;
    }
}
