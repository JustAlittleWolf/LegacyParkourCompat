package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.InputVectorHook;
import net.minecraft.world.phys.Vec3;

/**
 * Preserve existing input-vector behavior below the independently established
 * Mojmap 1.16.2--1.16.5 Entity#getInputVector / Vec3#normalize coverage.
 * This boundary does not assert historical arithmetic for earlier profiles;
 * their independent InputAccelerationHook continues to run before this seam.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class InputVectorNativeBoundary implements InputVectorHook {
    @Override
    public Vec3 inputVector(Vec3 input, float speed, float yaw, VanillaFn<Vec3> vanilla) {
        return vanilla.get();
    }
}
