package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;

import java.lang.annotation.*;

/**
 * Marks a historical movement delta.
 *
 * <p>{@link #emulates()} is the parkour version this implementation matches.
 * Vanilla replaced that behaviour in {@link ParkourVersion#next()}: a change
 * that emulates {@link ParkourVersion#V1_8} is the 1.8 behaviour, replaced in
 * 1.9. If several deltas exist for the same mechanic, the one whose
 * {@code emulates} is closest to (but not older than) the selection wins.
 *
 * <p>Example from {@code AGENTS.md}: mechanic {@code A.A} changed in 1.9
 * (emulates {@code V1_8}) and 1.11 (emulates {@code V1_10}). Selecting 1.8
 * loads the {@code V1_8} change, not the {@code V1_10} one.
 *
 * <pre>{@code
 * @MovementChange(emulates = ParkourVersion.V1_8)
 * public final class LadderCollision implements BlockCollisionShapeHook {
 *     public String blockId() { return "minecraft:ladder"; }
 *     public Optional<VoxelShape> collisionShape(...) { return Optional.of(...); }
 * }
 * }</pre>
 *
 * <p>A class may implement several {@link MechanicType} hooks; each is registered
 * independently under its own mechanic key. {@link VersionedMechanic#variant()}
 * still applies to every hook on that class.
 *
 * <p>The public concrete class must not reference mixins. Client initialization
 * discovers annotated classes below {@code change.v<version>} packages and
 * registers every implemented {@link MechanicType} interface. This annotation,
 * not the package name, defines the version. Supply a public no-argument
 * constructor, or one {@link Factory} method for keyed variants.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MovementChange {
    /**
     * Parkour version whose behaviour this implements. Vanilla changed in the
     * next {@link ParkourVersion} in enum order.
     */
    ParkourVersion emulates();

    /**
     * Supplies this change's keyed variants instead of its no-argument constructor.
     * Annotate exactly one public static no-argument method returning a nonempty
     * {@code List} of instances of its declaring change class. Each instance is
     * shared across all of that class's hooks; variants must have unique keys.
     * The factory runs once during client initialization, after block registration.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Factory {
    }
}
