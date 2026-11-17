package me.wolfii.legacyparkourcompat.change.v1_18_2;

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
 * Preserves the 1.18.2 Llama.positionRider 0.6 height factor for a default adult vanilla rider.
 * Its exact Mojmap source retains the 1.18 height writer; native 26.2 Entity.getPassengerRidingPosition,
 * Entity.positionRider, and EntityTypes.LLAMA supply the preserved native X/Z coordinates.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
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

        Vec3 nativePosition = llama.getPassengerRidingPosition(passenger)
            .subtract(passenger.getVehicleAttachmentPoint(llama));
        double historicalY = llama.getY() + llama.getBbHeight() * 0.6 + PLAYER_RIDING_OFFSET;
        return Optional.of(new Placement(
            new Vec3(nativePosition.x, historicalY, nativePosition.z),
            RotationEffect.KEEP_NATIVE
        ));
    }
}
