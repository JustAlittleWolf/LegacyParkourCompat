package me.wolfii.legacyparkourcompat.change.boat;

import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Placement;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.RotationEffect;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Shared 1.8 boat seat operation for its two exact mount-height constants. */
public final class LegacyBoatRiderPlacement {
    private LegacyBoatRiderPlacement() {
    }

    public static Optional<Placement> ordinaryOakBoat(Boat boat, LocalPlayer rider, double mountHeight) {
        if (!BoatRiderWitness.isOrdinaryOakBoatRider(boat, rider)) {
            return Optional.empty();
        }

        double d = Math.cos(boat.getYRot() * Math.PI / 180.0) * 0.4;
        double e = Math.sin(boat.getYRot() * Math.PI / 180.0) * 0.4;
        return Optional.of(new Placement(
            new Vec3(boat.getX() + d, boat.getY() + mountHeight + -0.35, boat.getZ() + e),
            RotationEffect.KEEP_NATIVE
        ));
    }
}
