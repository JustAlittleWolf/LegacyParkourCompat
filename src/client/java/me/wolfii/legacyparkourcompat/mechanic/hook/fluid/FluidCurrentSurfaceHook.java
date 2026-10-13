package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.world.level.material.Fluid;
import java.util.OptionalDouble;

/** Surface arithmetic used only for the current-depth update of a native-admitted cell. */
@MechanicType("fluid.current_surface")
public interface FluidCurrentSurfaceHook extends VersionedMechanic {
    OptionalDouble currentSurface(ParkourVersion selected, Fluid fluid, int blockY, float fluidHeight);
}
