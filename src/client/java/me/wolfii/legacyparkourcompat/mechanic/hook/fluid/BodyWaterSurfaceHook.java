package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

/**
 * Supplies a historical fluid surface for the local player's body-water projection.
 *
 * <p>The caller must use this only for body-water contact/depth sampling. In 1.13,
 * Ornithe Feather, {@code net.minecraft.entity.Entity#m_69693160(Tag)} reads
 * {@code FluidState#getHeight()}, whose {@code FlowingFluid#getHeight(FluidState)}
 * returns {@code state.getLevel() / 9.0F}. In 26.2 unobfuscated sources,
 * {@code net.minecraft.world.entity.EntityFluidInteraction#update(Entity, boolean)}
 * uses contextual {@code FluidState#getHeight(BlockGetter, BlockPos)}, which can
 * promote the surface when the same fluid is above. This hook is intentionally a
 * separate body projection input; it must not replace eye-contact or current inputs.
 *
 * <p>An empty result preserves native sampling for unsupported contexts and states.
 */
@MechanicType("fluid.body_water_surface")
public interface BodyWaterSurfaceHook extends VersionedMechanic {
    Optional<Float> historicalHeight(Entity entity, FluidState state);
}
