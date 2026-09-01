/**
 * Versioned movement deltas live in this package (and subpackages).
 *
 * <p>Each class implements exactly one {@code @MechanicType} hook and is annotated
 * with {@code @MovementChange(emulates = ...)}. Register it from a
 * Fabric entrypoint {@code legacyparkourcompat:movement-change} or by calling
 * {@link me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry#register(Object)}.
 *
 * <p>Do not write mixins here. The injection framework already dispatches to the
 * active implementation for the selected version.
 *
 * <p>Each {@link me.wolfii.legacyparkourcompat.api.ParkourVersion} has its own
 * subpackage (for example {@code change.v1_8}) with a {@code MovementChanges}
 * entrypoint and one class per mechanic delta.
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
 * {@link me.wolfii.legacyparkourcompat.api.ParkourVersion#V1_9}. Current/disabled
 * applies no changes.
 */
package me.wolfii.legacyparkourcompat.change;
