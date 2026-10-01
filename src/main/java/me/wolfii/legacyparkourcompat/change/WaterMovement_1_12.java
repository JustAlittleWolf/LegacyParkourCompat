package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSneakBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterTravelBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** The independent water movement deltas established at the 1.13 group boundary. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterMovement_1_12 implements
    WaterJumpBehavior,
    WaterSneakBehavior,
    WaterTravelBehavior,
    SwimmingBehavior,
    SprintingBehavior,
    PlayerDimensionsBehavior
{
    @Override
    public void jumpInWater(Player player) {
        player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.04F, 0.0));
    }

    @Override
    public boolean shouldApplyDownwardImpulse(Player player, boolean vanilla) {
        return false;
    }

    @Override
    public float sprintSlowdown(Player player, float vanilla) {
        return player.isSprinting() ? 0.8F : vanilla;
    }

    @Override
    public Vec3 gravityAdjustedMovement(
        Player player,
        double baseGravity,
        boolean falling,
        Vec3 movement,
        Vec3 vanilla
    ) {
        if (baseGravity == 0.0D) {
            return movement;
        }
        return new Vec3(movement.x, movement.y - 0.02D, movement.z);
    }

    @Override
    public boolean isSwimming(Player player, boolean vanilla) {
        return false;
    }

    @Override
    public void updateSwimming(Player player, VanillaCall vanilla) {
        vanilla.run();
    }

    @Override
    public boolean allowShallowWaterSprint(Player player, boolean vanilla) {
        return true;
    }

    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.SWIMMING) {
            return EntityDimensions.scalable(0.6F, player.isShiftKeyDown() ? 1.65F : 1.8F);
        }
        return vanilla;
    }
}
