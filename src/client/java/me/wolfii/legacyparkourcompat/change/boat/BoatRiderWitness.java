package me.wolfii.legacyparkourcompat.change.boat;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.vehicle.boat.Boat;

/** Shared exact rider witness and seat-coordinate terms for the legacy oak-boat operation. */
public final class BoatRiderWitness {
    private BoatRiderWitness() {
    }

    public static boolean isOrdinaryOakBoatRider(Boat boat, LocalPlayer rider) {
        if (boat == null || rider == null || boat.getClass() != Boat.class || rider.getClass() != LocalPlayer.class
            || boat.level() != rider.level() || !boat.level().isClientSide()
            || !rider.isPassenger() || rider.getVehicle() != boat || !boat.hasPassenger(rider)
            || boat.getPassengers().size() != 1 || boat.getPassengers().getFirst() != rider) {
            return false;
        }

        var type = BuiltInRegistries.ENTITY_TYPE.getKey(boat.getType());
        return type != null && "minecraft".equals(type.getNamespace()) && "oak_boat".equals(type.getPath());
    }

}
