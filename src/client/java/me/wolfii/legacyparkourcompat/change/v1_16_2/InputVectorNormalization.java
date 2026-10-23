package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.InputVectorHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * Mojmap 1.16.2, 1.16.3, 1.16.4 and 1.16.5 Entity#getInputVector calls
 * Vec3#normalize, whose divisor is Mth#sqrt(double), returning (float)Math.sqrt.
 * Mojmap 1.17 and 1.17.1 Vec3#normalize instead retain Math.sqrt's double result;
 * Entity#getInputVector retains its gates, scale and float yaw rotation.
 * Only the normalization divisor rounds to float; coordinate division stays double.
 * This operation is reached only when the separate InputAccelerationHook leaves
 * moveRelative native, preserving the earlier float input-acceleration operation.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class InputVectorNormalization implements InputVectorHook {
    @Override
    public Vec3 inputVector(Vec3 input, float speed, float yaw, VanillaFn<Vec3> vanilla) {
        double squaredLength = input.lengthSqr();
        if (squaredLength < 1.0E-7) {
            return Vec3.ZERO;
        }
        Vec3 normalized = input;
        if (squaredLength > 1.0) {
            double divisor = (float)Math.sqrt(input.x * input.x + input.y * input.y + input.z * input.z);
            normalized = divisor < 1.0E-4 ? Vec3.ZERO
                : new Vec3(input.x / divisor, input.y / divisor, input.z / divisor);
        }
        Vec3 movement = normalized.scale(speed);
        float sin = Mth.sin(yaw * (float)(Math.PI / 180.0));
        float cos = Mth.cos(yaw * (float)(Math.PI / 180.0));
        return new Vec3(movement.x * cos - movement.z * sin, movement.y, movement.z * cos + movement.x * sin);
    }
}
