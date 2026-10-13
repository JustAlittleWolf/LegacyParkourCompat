package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.world.level.material.Fluid;
import java.util.OptionalDouble;
import net.minecraft.world.phys.AABB;

/** Depth origin used only for the current-depth update of a native-admitted cell. */
@MechanicType("fluid.current_depth_origin")
public interface FluidCurrentDepthOriginHook extends VersionedMechanic {
    OptionalDouble currentDepthOrigin(ParkourVersion selected, Fluid fluid, AABB rawBoundingBox);
}
