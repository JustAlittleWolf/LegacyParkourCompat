package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionFluidHook;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class MountedBoatWaterInteraction implements FluidInteractionFluidHook {
    @Override
    public FluidState fluidState(Entity entity, FluidState vanilla) {
        if (entity instanceof Player player
            && player.getVehicle() instanceof AbstractBoat boat && !boat.isUnderWater()
            && vanilla.is(FluidTags.WATER)) {
            return Fluids.EMPTY.defaultFluidState();
        }
        return vanilla;
    }
}
