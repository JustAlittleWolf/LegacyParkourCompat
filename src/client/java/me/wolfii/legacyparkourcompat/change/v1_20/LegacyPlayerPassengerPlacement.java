package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Carrier;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Input;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Invocation;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Placement;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.RotationEffect;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Neutral 1.20.1 seat-position arithmetic for the 1.20.2 passenger attachment writer.
 *
 * <p>The caller extracts only the listed source operands from the live vehicle and rider. This
 * helper does not tick, move, animate, or otherwise simulate a vehicle. Its carrier table maps all
 * 44 1.20.2 attachment declarations back to the 1.20.1 offset behavior inherited or overridden
 * by the same carrier family. Unknown carriers and non-ride-tick invocations remain native.
 *
 * <p>Source anchors: Minecraft 1.20.1 Mojmap {@code Entity.positionRider(Entity, MoveFunction)},
 * {@code Entity.getPassengersRidingOffset()}, {@code Player.getMyRidingOffset()}, and the
 * {@code Boat}, {@code Camel}, {@code Chicken}, {@code AbstractHorse}, and {@code Llama}
 * {@code positionRider} overrides; Minecraft 1.20.2 Mojmap
 * {@code Entity.positionRider(Entity, MoveFunction)},
 * {@code Entity.getPassengerRidingPosition(Entity)},
 * {@code LivingEntity.getPassengerRidingPosition(Entity)},
 * {@code LivingEntity.getMyRidingOffset(Entity)}, and the inventoried
 * {@code getPassengerAttachmentPoint(Entity, EntityDimensions, float)} declarations.
 */
public final class LegacyPlayerPassengerPlacement {
    // 1.20.1 Player.getMyRidingOffset() returns this double directly; unlike 1.20.2's
    // LivingEntity.getMyRidingOffset(vehicle), this source path does not multiply by Player scale.
    private static final double PLAYER_RIDING_OFFSET = -0.35;

    private LegacyPlayerPassengerPlacement() {
    }

    /**
     * Calculates the final coordinates written during a local client passenger ride tick.
     *
     * <p>The actual entity relation and logical side are checked here as well as by the operation
     * hook caller so an accidental invocation on a remote/server/unattached player stays native.
     */
    public static Optional<Placement> calculate(LocalPlayer passenger, Entity vehicle, Input input) {
        if (passenger == null || vehicle == null || input == null
            || !passenger.level().isClientSide()
            || vehicle.level() != passenger.level()
            || input.invocation() != Invocation.CLIENT_RIDE_TICK
            || !input.hasPassenger()
            || !passenger.isPassenger()
            || passenger.getVehicle() != vehicle
            || !vehicle.hasPassenger(passenger)) {
            return Optional.empty();
        }

        Carrier carrier = input.carrier();
        if (carrier == Carrier.UNKNOWN) {
            return Optional.empty();
        }

        Vec3 position = switch (carrier) {
            case CAMEL -> camel(input);
            case CHICKEN -> chicken(input);
            case ABSTRACT_HORSE, CHESTED_HORSE, SKELETON_HORSE -> horse(input);
            case LLAMA -> llama(input);
            case BOAT -> boat(input);
            default -> base(input, oldVehicleOffset(input));
        };
        if (position == null) {
            return Optional.empty();
        }

        RotationEffect rotation = switch (carrier) {
            case CAMEL -> RotationEffect.CLAMP_TO_VEHICLE;
            case ABSTRACT_HORSE, CHESTED_HORSE, SKELETON_HORSE -> input.horseStandAnimO() > 0.0F
                ? RotationEffect.KEEP_NATIVE
                : RotationEffect.SUPPRESS_NATIVE_BODY_YAW_COPY;
            default -> RotationEffect.KEEP_NATIVE;
        };
        return Optional.of(new Placement(position, rotation));
    }

    private static Vec3 base(Input input, double vehicleOffset) {
        return new Vec3(
            input.vehicleX(),
            input.vehicleY() + vehicleOffset + PLAYER_RIDING_OFFSET,
            input.vehicleZ()
        );
    }

    private static Vec3 camel(Input input) {
        if (input.passengerIndex() < 0) {
            return null;
        }

        boolean frontSeat = input.passengerIndex() == 0;
        float side = 0.5F;
        float yOffset = (float)(input.vehicleRemoved()
            ? 0.01F
            : input.camelBodyAnchorYOffset() + PLAYER_RIDING_OFFSET);
        if (input.passengerCount() > 1) {
            if (!frontSeat) {
                side = -0.7F;
            }
        }

        Vec3 seat = new Vec3(0.0, 0.0, side)
            .yRot(-input.vehicleYBodyRot() * (float)(Math.PI / 180.0));
        return new Vec3(input.vehicleX() + seat.x, input.vehicleY() + yOffset, input.vehicleZ() + seat.z);
    }

    private static Vec3 chicken(Input input) {
        float radians = input.vehicleYBodyRot() * (float)(Math.PI / 180.0);
        return new Vec3(
            input.vehicleX() + 0.1F * Mth.sin(radians),
            input.chickenYAtHalf() + PLAYER_RIDING_OFFSET + 0.0,
            input.vehicleZ() - 0.1F * Mth.cos(radians)
        );
    }

    private static Vec3 horse(Input input) {
        double vehicleOffset = oldVehicleOffset(input);
        if (input.horseStandAnimO() > 0.0F) {
            float radians = input.vehicleYBodyRot() * (float)(Math.PI / 180.0);
            float horizontal = 0.7F * input.horseStandAnimO();
            float vertical = 0.15F * input.horseStandAnimO();
            return new Vec3(
                input.vehicleX() + horizontal * Mth.sin(radians),
                input.vehicleY() + vehicleOffset + PLAYER_RIDING_OFFSET + vertical,
                input.vehicleZ() - horizontal * Mth.cos(radians)
            );
        }
        return base(input, vehicleOffset);
    }

    private static Vec3 llama(Input input) {
        float radians = input.vehicleYBodyRot() * (float)(Math.PI / 180.0);
        return new Vec3(
            input.vehicleX() + 0.3F * Mth.sin(radians),
            input.vehicleY() + oldVehicleOffset(input) + PLAYER_RIDING_OFFSET,
            input.vehicleZ() - 0.3F * Mth.cos(radians)
        );
    }

    private static Vec3 boat(Input input) {
        float side = input.boatSinglePassengerXOffset();
        float yOffset = (float)((input.vehicleRemoved() ? 0.01F : oldVehicleOffset(input))
            + PLAYER_RIDING_OFFSET);
        if (input.passengerCount() > 1) {
            side = input.passengerIndex() == 0 ? 0.2F : -0.6F;
        }

        float rotation = -input.vehicleYRot() * (float)(Math.PI / 180.0) - (float)(Math.PI / 2);
        Vec3 seat = new Vec3(side, 0.0, 0.0).yRot(rotation);
        return new Vec3(input.vehicleX() + seat.x, input.vehicleY() + yOffset, input.vehicleZ() + seat.z);
    }

    private static double oldVehicleOffset(Input input) {
        Carrier carrier = input.carrier();
        return switch (carrier) {
            case CAMEL -> input.camelPoseHeight() - (input.vehicleBaby() ? 0.35F : 0.6F);
            case CHESTED_HORSE -> input.vehicleDimensionsHeight() * 0.75 - 0.25;
            case LLAMA -> input.vehicleBoundingBoxHeight() * 0.6;
            case SKELETON_HORSE -> input.vehicleDimensionsHeight() * 0.75 - 0.1875;
            case SNIFFER -> 1.8;
            case ENDER_DRAGON -> input.dragonBodyHeight();
            case HOGLIN, ZOGLIN -> input.vehicleBoundingBoxHeight() - (input.vehicleBaby() ? 0.2 : 0.15);
            case PHANTOM -> input.vehicleEyeHeight();
            case PIGLIN -> input.vehicleBoundingBoxHeight() * 0.92;
            case RAVAGER -> 2.1;
            case SPIDER -> input.vehicleBoundingBoxHeight() * 0.5F;
            case STRIDER -> input.vehicleBoundingBoxHeight() - 0.19
                + 0.12F * Mth.cos(input.striderWalkPosition() * 1.5F) * 2
                    * Math.min(0.25F, input.striderWalkSpeed());
            case ABSTRACT_MINECART, MINECART -> 0.0;
            case BOAT -> input.bambooBoat() ? 0.25 : -0.1;
            case UNKNOWN -> Double.NaN;
            default -> input.vehicleDimensionsHeight() * 0.75;
        };
    }
}
