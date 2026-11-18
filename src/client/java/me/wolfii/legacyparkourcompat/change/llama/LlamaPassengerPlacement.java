package me.wolfii.legacyparkourcompat.change.llama;

import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Placement;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.RotationEffect;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Reconstructs only the historical Llama rider Y term on the existing passenger-position path. */
public final class LlamaPassengerPlacement {
    private static final float DEFAULT_LLAMA_WIDTH = 0.9F;
    private static final float DEFAULT_LLAMA_HEIGHT = 1.87F;
    private static final double PLAYER_RIDING_OFFSET = -0.35;

    private LlamaPassengerPlacement() {
    }

    public static Optional<Placement> defaultAdult(LocalPlayer passenger, Entity vehicle, double heightFactor) {
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
        if (typeId == null || !"minecraft".equals(typeId.getNamespace())
            || !("llama".equals(typeId.getPath()) || "trader_llama".equals(typeId.getPath()))) {
            return Optional.empty();
        }

        Vec3 nativePosition = llama.getPassengerRidingPosition(passenger)
            .subtract(passenger.getVehicleAttachmentPoint(llama));
        double historicalY = llama.getY() + llama.getBbHeight() * heightFactor + PLAYER_RIDING_OFFSET;
        return Optional.of(new Placement(
            new Vec3(nativePosition.x, historicalY, nativePosition.z),
            RotationEffect.KEEP_NATIVE
        ));
    }
}
