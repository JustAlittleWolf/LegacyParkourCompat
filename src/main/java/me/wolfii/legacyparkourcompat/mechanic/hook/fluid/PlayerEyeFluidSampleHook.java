package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Selects the Y coordinate used by the modern player eye-fluid scan. */
@MechanicType("fluid.player_eye_sample")
public interface PlayerEyeFluidSampleHook extends VersionedMechanic {
    double sampleY(Entity entity, double eyeY);
}
