package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.EyeFluidCacheHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import java.util.Set;

/**
 * Samples the historical eye block into entity-owned state after the native body-fluid update.
 *
 * <p>Source comparison: Minecraft 1.21.11 Mojmap, Entity#baseTick, Entity#updateFluidOnEyes,
 * Entity#isEyeInFluid, and AbstractBoat#isUnderWater; Minecraft 26.1 unobfuscated,
 * Entity#baseTick, Entity#updateFluidInteraction, Entity#isEyeInFluid,
 * EntityFluidInteraction#update, and AbstractBoat#modifyPassengerFluidInteractionBox. The 26.2
 * decompiled EntityFluidInteraction source is byte-identical to 26.1. The accepted audit records
 * that 26.2's raw client artifact hash is not bound by retained provenance, so no stronger claim
 * about 26.2 client identity is made here.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class EyeFluidPointCache implements EyeFluidCacheHook {
    @Override
    public boolean overridesVanillaCache() {
        return true;
    }

    @Override
    public void refresh(Entity entity, Set<TagKey<Fluid>> cache) {
        cache.clear();
        double eyeY = entity.getEyeY();
        if (entity.getVehicle() instanceof AbstractBoat boat
            && !boat.isUnderWater()
            && boat.getBoundingBox().maxY >= eyeY
            && boat.getBoundingBox().minY <= eyeY) {
            return;
        }

        BlockPos pos = BlockPos.containing(entity.getX(), eyeY, entity.getZ());
        FluidState fluidState = entity.level().getFluidState(pos);
        double fluidTop = pos.getY() + fluidState.getHeight(entity.level(), pos);
        if (fluidTop > eyeY) {
            fluidState.getTags().forEach(cache::add);
        }
    }
}
