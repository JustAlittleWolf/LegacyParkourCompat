package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeFluidSampleHook;
import net.minecraft.world.entity.Entity;

/**
 * Restores the lowered eye-fluid sample used through 1.20.4.
 *
 * <p>In 1.20.4 (Mojmap), {@code Entity.updateFluidOnEyes} uses
 * {@code getEyeY() - 0.11111111F}; 1.20.5 and 1.20.6 (Mojmap) use
 * {@code getEyeY()}. In 26.2 (unobfuscated), the corresponding scan is
 * {@code EntityFluidInteraction.update}; the hook applies the lowered sample
 * to the eye-fluid comparisons while preserving scanner order. Mojmap 1.19.4
 * {@code Entity.updateFluidOnEyes} uses the same lowered coordinate. The
 * cached scan is shared with local-player predicates and retains its vanilla
 * update order. It does not recreate the historical boat gate:
 * 1.16 tests point containment in {@code Entity.updateFluidOnEyes}, whereas
 * 1.20.4 uses vertical overlap in that method.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class PlayerEyeFluidSample implements PlayerEyeFluidSampleHook {
    @Override
    public double sampleY(Entity entity, double eyeY) {
        return eyeY - 0.11111111F;
    }
}
