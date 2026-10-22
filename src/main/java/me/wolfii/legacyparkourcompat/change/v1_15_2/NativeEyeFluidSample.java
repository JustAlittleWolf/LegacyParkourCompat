package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeFluidSampleHook;
import net.minecraft.world.entity.Entity;

/**
 * Keeps the later cached eye-fluid sample operation inactive for older profiles.
 *
 * <p>In 1.15.2 (Mojmap), {@code Entity.isUnderLiquid} samples at
 * {@code getEyeY()} and applies the {@code 0.11111111F} threshold inside its
 * fluid-height predicate. In 1.16 (Mojmap), {@code Entity.tick} calls the new
 * cached {@code Entity.updateFluidOnEyes} operation, which samples at
 * {@code getEyeY() - 0.11111111F}. Preserve the unshifted sample here; the
 * earlier predicate is a separate movement operation.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeEyeFluidSample implements PlayerEyeFluidSampleHook {
    @Override
    public double sampleY(Entity entity, double eyeY) {
        return eyeY;
    }
}
