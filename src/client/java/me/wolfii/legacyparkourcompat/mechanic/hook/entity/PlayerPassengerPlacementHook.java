package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Resolves the complete client-side seat placement operation for the local player.
 *
 * <p>The integration calls this from the common passenger position callback, after the vehicle's
 * existing {@code hasPassenger} gate and before the callback completes. The versioned change
 * resolves source operands from the actual vehicle's supported carrier type and current
 * pose/animation/passenger state. The change owns seat geometry only; vehicle ticking and motion
 * remain vanilla.
 *
 * <p>Return {@link Optional#empty()} to leave the native callback untouched. A returned placement
 * replaces only the local player's position write. Existing vehicle {@code positionRider}
 * implementations continue after that write, preserving Boat yaw delta/clamping and Chicken
 * body-yaw copy. The result identifies when the new AbstractHorse unconditional body-yaw copy
 * must be suppressed. {@link RotationEffect#CLAMP_TO_VEHICLE} represents the additional Camel
 * clamp present in 1.20.1 and absent from its 1.20.2 attachment-point path.
 *
 * <p>Only a logical-client {@link LocalPlayer} that is still a passenger of the supplied vehicle
 * is eligible. Remote {@code Player} passengers, non-player passengers, invalid relations, and
 * server execution must use the native operation.
 *
 * <p>The source operation spans more than the Player offset. 1.20.1
 * {@code Entity.positionRider(Entity, MoveFunction)} and the custom overrides in
 * {@code Boat.positionRider}, {@code Camel.positionRider}, {@code Chicken.positionRider},
 * {@code AbstractHorse.positionRider}, and {@code Llama.positionRider} consume the old passenger
 * offset API; 1.20.2 {@code Entity.positionRider(Entity, MoveFunction)} dispatches through
 * {@code getPassengerRidingPosition} / {@code getPassengerAttachmentPoint}. The historic formula
 * must therefore cover the complete old offset declaration set (Entity, Camel,
 * AbstractChestedHorse, Llama, SkeletonHorse, Sniffer, EnderDragon, Hoglin, Phantom, Piglin,
 * Ravager, Spider, Strider, Zoglin, AbstractMinecart, and Boat), plus those five custom position
 * overrides. For attachment-only carriers, use the corresponding old inherited or overridden
 * offset behavior. Preserve source gates, float/double conversions, operation order, and the
 * vehicle-specific inputs; do not substitute the new attachment vector or a fixed Player delta.
 *
 * <p>Exact source references: Minecraft 1.20.1 Mojmap
 * {@code net.minecraft.world.entity.Entity.positionRider(Entity, Entity.MoveFunction)},
 * {@code Player.getMyRidingOffset()}, and the vehicle overrides above; Minecraft 1.20.2 Mojmap
 * {@code Entity.positionRider(Entity, Entity.MoveFunction)},
 * {@code Entity.getPassengerRidingPosition(Entity)},
 * {@code LivingEntity.getPassengerRidingPosition(Entity)},
 * {@code LivingEntity.getMyRidingOffset(Entity)}, and the carrier
 * {@code getPassengerAttachmentPoint(Entity, EntityDimensions, float)} overrides.
 */
@MechanicType("player.passenger_placement")
public interface PlayerPassengerPlacementHook extends VersionedMechanic {
    /**
     * Returns the historical seat position and any operation side effect not retained by the
     * native vehicle method. Empty preserves the native position callback and all native effects.
     */
    Optional<Placement> placement(LocalPlayer passenger, Entity vehicle);

    /**
     * Source operands needed to replay the 1.20.1 position operation. The integration extracts or
     * reconstructs each value to match the old getter at the current state; none may be copied from
     * the new attachment point. Fields intentionally distinguish {@code dimensions.height},
     * bounding-box height, pose height, eye height, and animation operands because the old
     * declarations use different getters and numeric types.
     *
     * <p>For a custom carrier, callers supply its exact 1.20.1 inputs: Camel's body-anchor offset,
     * Boat's one-passenger side offset and variant, Chicken's {@code getY(0.5)} value, horse stand
     * animation, and Strider walk position/speed. The source Animal passenger branches in Boat and
     * Camel are false for this {@link LocalPlayer} operation. A receiver must reject
     * {@link Carrier#UNKNOWN} and any invocation outside the client ride-tick position callback.
     */
    record Input(
        Carrier carrier,
        Invocation invocation,
        boolean hasPassenger,
        int passengerIndex,
        int passengerCount,
        boolean vehicleRemoved,
        boolean vehicleBaby,
        boolean bambooBoat,
        double vehicleX,
        double vehicleY,
        double vehicleZ,
        float vehicleDimensionsHeight,
        float vehicleBoundingBoxHeight,
        float camelPoseHeight,
        float vehicleEyeHeight,
        float dragonBodyHeight,
        float striderWalkPosition,
        float striderWalkSpeed,
        double camelBodyAnchorYOffset,
        double chickenYAtHalf,
        float boatSinglePassengerXOffset,
        float vehicleYRot,
        float vehicleYBodyRot,
        float horseStandAnimO
    ) {
        public Input {
            if (carrier == null || invocation == null) {
                throw new IllegalArgumentException("Passenger placement carrier and invocation are required");
            }
        }
    }

    /** The common passenger tick is the only placement call in this operation's scope. */
    enum Invocation {
        CLIENT_RIDE_TICK,
        OTHER
    }

    /**
     * Runtime source carrier for the 1.20.2 attachment declaration inventory. The 44 declaration
     * owners are represented directly, with {@code ABSTRACT_MINECART} matching the declaration
     * owner while {@code MINECART} remains a convenient concrete-family alias. {@code CHESTED_HORSE}
     * and {@code PIGLIN} distinguish 1.20.1 subclass overrides that are not 1.20.2 attachment
     * declaration owners. The legacy calculator maps each carrier to its 1.20.1 inherited or
     * overridden offset; {@code OTHER_BASE} is for carriers with no declaration in either
     * inventory, and UNKNOWN deliberately falls back to native behavior.
     */
    enum Carrier {
        BASE,
        CAT,
        CAMEL,
        CHICKEN,
        COW,
        FOX,
        FROG,
        GOAT,
        ABSTRACT_HORSE,
        CHESTED_HORSE,
        LLAMA,
        SKELETON_HORSE,
        OCELOT,
        PANDA,
        PARROT,
        PIG,
        SHEEP,
        SNIFFER,
        TURTLE,
        WOLF,
        ENDER_DRAGON,
        ABSTRACT_ILLAGER,
        CAVE_SPIDER,
        ELDER_GUARDIAN,
        ENDERMAN,
        ENDERMITE,
        GHAST,
        GUARDIAN,
        HOGLIN,
        HUSK,
        PHANTOM,
        ABSTRACT_PIGLIN,
        PIGLIN,
        RAVAGER,
        SILVERFISH,
        SLIME,
        SPIDER,
        STRIDER,
        VEX,
        WARDEN,
        WITCH,
        ZOGLIN,
        ZOMBIE,
        ZOMBIE_VILLAGER,
        ZOMBIFIED_PIGLIN,
        ABSTRACT_MINECART,
        MINECART,
        BOAT,
        OTHER_BASE,
        UNKNOWN
    }

    /**
     * The coordinates written by the move callback and the rotation behavior for this complete
     * historical placement operation. KEEP_NATIVE retains Boat's delta-rotation/clamp and
     * Chicken's body-yaw copy (and the matching horse copy when stand animation is positive).
     * CLAMP_TO_VEHICLE restores Camel's old clamp. SUPPRESS_NATIVE_BODY_YAW_COPY removes only the
     * new AbstractHorse copy when its old stand-animation gate would have skipped it.
     */
    record Placement(Vec3 position, RotationEffect rotationEffect) {
        public Placement {
            if (position == null || rotationEffect == null) {
                throw new IllegalArgumentException("Passenger placement fields must not be null");
            }
        }
    }

    /**
     * Native Boat/Chicken effects and the matching AbstractHorse body-yaw copy are retained.
     * Camel's 1.20.1 override additionally clamps its passenger rotation; that override is absent
     * in 1.20.2 and must be restored after the replacement position write. AbstractHorse in
     * 1.20.1 copies body yaw only while its stand animation is positive; the 1.20.2 override does
     * so unconditionally, so the operation must suppress that new copy when stand animation is
     * zero.
     */
    enum RotationEffect {
        KEEP_NATIVE,
        CLAMP_TO_VEHICLE,
        SUPPRESS_NATIVE_BODY_YAW_COPY
    }
}
