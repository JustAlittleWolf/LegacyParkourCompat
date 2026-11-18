package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.BodyWaterSurfaceHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

/**
 * Restores the 1.13 body sampler's own fluid height as a separate projection input.
 *
 * <p>In 1.13, 1.13.1 and 1.13.2 (Ornithe Feather),
 * {@code net.minecraft.fluid.FlowingFluid#getHeight(FluidState)} returns
 * {@code state.getLevel() / 9.0F}; {@code net.minecraft.entity.Entity#m_69693160}
 * uses that state-only height for body contact and maximum immersion. Stable
 * 1.14 Ornithe Feather {@code Entity#m_69693160} reads contextual height, which
 * {@code net.minecraft.fluid.FlowingFluid#getHeight} promotes when matching
 * fluid is immediately above. In 26.2 (unobfuscated),
 * {@code net.minecraft.world.level.material.FlowingFluid#getOwnHeight(FluidState)}
 * returns {@code fluidState.getAmount() / 9.0F}, while
 * {@code getHeight(FluidState, BlockGetter, BlockPos)} may return {@code 1.0F}
 * when the same fluid exists above. Keep the float arithmetic and do not pass
 * contextual height into this body-only hook.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class BodyWaterSurface implements BodyWaterSurfaceHook {
    @Override
    public Optional<Float> historicalHeight(Entity entity, FluidState state) {
        if (!(entity instanceof LocalPlayer) || entity.isPassenger() || !state.is(FluidTags.WATER)
            || (state.getType() != Fluids.WATER && state.getType() != Fluids.FLOWING_WATER)) {
            return Optional.empty();
        }
        return Optional.of(state.getOwnHeight());
    }
}
