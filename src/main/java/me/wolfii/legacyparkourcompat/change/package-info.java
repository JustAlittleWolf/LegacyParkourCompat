/**
 * Versioned movement deltas live in this package (and subpackages).
 *
 * <p>Each class is annotated with {@code @MovementChange(emulates = ...)} and
 * implements one or more {@code @MechanicType} hooks. Group one kind of
 * historical fix in one file; a class that needs several mixin-free hooks
 * (for example pose selection and eye height) implements each interface. Register it from a
 * Fabric entrypoint {@code legacyparkourcompat:movement-change} or by calling
 * {@link me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry#register(Object)}.
 *
 * <p>Do not write mixins here. The injection framework already dispatches to the
 * active implementation for the selected version.
 *
 * <p>Each {@link me.wolfii.legacyparkourcompat.api.ParkourVersion} has its own
 * subpackage (for example {@code change.v1_8}) with a {@code MovementChanges}
 * entrypoint and one class per mechanic delta. Keyed block families expose
 * {@code register(MovementChangeRegistry)} so the change class, not the
 * entrypoint, decides which blocks it applies to
 * ({@link me.wolfii.legacyparkourcompat.change.BlockChanges#registerEach}).
 *
 * <pre>{@code
 * // change.v1_8.LadderCollision
 * @MovementChange(emulates = ParkourVersion.V1_8)
 * public final class LadderCollision implements BlockCollisionShape {
 *     public String blockId() { return "minecraft:ladder"; }
 *     public Optional<VoxelShape> collisionShape(...) { return Optional.of(onePixelLadder); }
 * }
 * }</pre>
 *
 * <p>Selecting {@link me.wolfii.legacyparkourcompat.api.ParkourVersion#V1_8} loads
 * every change whose {@code emulates} is that version or later, keeping the
 * closest when the same mechanic changed more than once. {@code 1.9.2} is
 * {@link me.wolfii.legacyparkourcompat.api.ParkourVersion#V1_9}. The running
 * Minecraft version ({@link me.wolfii.legacyparkourcompat.api.ParkourVersion#CURRENT})
 * applies no changes.
 */
package me.wolfii.legacyparkourcompat.change;
