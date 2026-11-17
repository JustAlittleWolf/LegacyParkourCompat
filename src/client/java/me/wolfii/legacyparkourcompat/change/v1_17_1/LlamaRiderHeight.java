package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Placement;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.RotationEffect;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Restores the 1.17.1 local-player Y placement for a default adult vanilla Llama passenger.
 * Minecraft 1.17.1 Mojmap Llama.positionRider adds getPassengersRidingOffset() and then
 * Player.getMyRidingOffset(); Llama.getPassengersRidingOffset returns getBbHeight() * 0.67.
 * Minecraft 1.18 Mojmap retains the writer but changes that factor to 0.6. Native 26.2 Mojmap
 * Entity.getPassengerRidingPosition, Entity.positionRider, Llama.getPassengerAttachmentPoint,
 * EntityTypes.LLAMA, Avatar.DEFAULT_VEHICLE_ATTACHMENT, and Entity.getVehicleAttachmentPoint
 * supply the current placement and rider attachment that this provider preserves for X/Z.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    private static final float DEFAULT_LLAMA_WIDTH = 0.9F;
    private static final float DEFAULT_LLAMA_HEIGHT = 1.87F;
    private static final double PLAYER_RIDING_OFFSET = -0.35;

    public LlamaRiderHeight() {
    }

    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        if (passenger == null || vehicle == null
            || !passenger.level().isClientSide()
            || vehicle.level() != passenger.level()
            || !passenger.isPassenger()
            || passenger.getVehicle() != vehicle
            || !vehicle.hasPassenger(passenger)
            || vehicle.getPassengers().size() != 1
            || vehicle.getPassengers().get(0) != passenger
            || passenger.getScale() != 1.0F
            || !(vehicle instanceof Llama llama)
            || llama.isBaby()
            || llama.getScale() != 1.0F
            || llama.getBbWidth() != DEFAULT_LLAMA_WIDTH
            || llama.getBbHeight() != DEFAULT_LLAMA_HEIGHT) {
            return Optional.empty();
        }

        var typeId = BuiltInRegistries.ENTITY_TYPE.getKey(llama.getType());
        if (!"minecraft".equals(typeId.getNamespace())
            || !("llama".equals(typeId.getPath()) || "trader_llama".equals(typeId.getPath()))) {
            return Optional.empty();
        }

        // Entity.positionRider subtracts the rider's current vehicle attachment from this point.
        // Reconstruct its native final coordinates so only the accepted Y term changes.
        Vec3 nativePosition = llama.getPassengerRidingPosition(passenger)
            .subtract(passenger.getVehicleAttachmentPoint(llama));
        double historicalY = llama.getY() + llama.getBbHeight() * 0.67 + PLAYER_RIDING_OFFSET;
        return Optional.of(new Placement(
            new Vec3(nativePosition.x, historicalY, nativePosition.z),
            RotationEffect.KEEP_NATIVE
        ));
    }
}
