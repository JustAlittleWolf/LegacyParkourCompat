package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterGravityBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSprintSlowdownBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterTravel implements WaterSprintSlowdownBehavior, WaterGravityBehavior {
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
}
