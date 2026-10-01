package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerFluidPushBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

@MovementChange(emulates = ParkourVersion.V1_19)
public final class BoatPassengerFluidPush_1_19 implements BoatPassengerFluidPushBehavior {
    @Override
    public boolean skipWaterCurrent(Player player, Entity vehicle) {
        return !player.getAbilities().flying && vehicle instanceof AbstractBoat boat && !boat.isUnderWater();
    }
}
