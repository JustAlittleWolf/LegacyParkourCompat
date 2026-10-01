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
public final class WaterMovement_1_12_PlayerDimensions implements PlayerDimensionsBehavior {
    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.SWIMMING) {
            return EntityDimensions.scalable(0.6F, player.isShiftKeyDown() ? 1.65F : 1.8F);
        }
        return vanilla;
    }
}
