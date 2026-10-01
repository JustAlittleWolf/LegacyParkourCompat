package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSprintGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSneakBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterTravelBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterMovement_1_12_WaterJump implements WaterJumpBehavior {
    @Override
    public void jumpInWater(Player player) {
        player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.04F, 0.0));
    }
}
