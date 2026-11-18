package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.BodyWaterSurfaceHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

/** Keeps pre-1.13 profiles on native body-water surface sampling. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class BodyWaterSurfaceFence implements BodyWaterSurfaceHook {
    @Override
    public Optional<Float> historicalHeight(Entity entity, FluidState state) {
        return Optional.empty();
    }
}
