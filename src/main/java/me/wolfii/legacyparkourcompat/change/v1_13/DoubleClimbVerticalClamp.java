package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClimbVerticalClampBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Before 1.14, the downward climb limit was the double literal -0.15. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class DoubleClimbVerticalClamp implements ClimbVerticalClampBehavior {
    @Override
    public Vec3 clampVertical(LivingEntity entity, Vec3 movement) {
        return entity.onClimbable() && movement.y == -0.15F
            ? new Vec3(movement.x, -0.15, movement.z)
            : movement;
    }
}
