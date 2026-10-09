package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidInteractionBoxBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class MountedFluidInteractionBox implements FluidInteractionBoxBehavior {
    @Override
    public @Nullable AABB interactionBox(Entity entity, @Nullable AABB vanilla) {
        if (vanilla != null && entity instanceof Player player
            && player.getVehicle() instanceof AbstractBoat boat && !boat.isUnderWater()) {
            return player.getBoundingBox().deflate(0.001);
        }
        return vanilla;
    }
}
