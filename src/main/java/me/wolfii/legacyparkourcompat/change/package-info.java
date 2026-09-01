/**
 * Versioned movement deltas live in this package (and subpackages).
 *
 * <p>Each class implements exactly one {@code @MechanicType} hook and is annotated
 * with {@code @MovementChange(vanillaChangedIn = "...")}. Register it from a
 * Fabric entrypoint {@code legacyparkourcompat:movement-change} or by calling
 * {@link me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry#register(Object)}.
 *
 * <p>Do not write mixins here. The injection framework already dispatches to the
 * active implementation for the selected version.
 *
 * <pre>{@code
 * @MovementChange(vanillaChangedIn = "1.9", emulates = "1.8")
 * public final class LadderCollision_1_8 implements BlockCollisionShape {
 *     public String blockId() { return "minecraft:ladder"; }
 *     public Optional<VoxelShape> collisionShape(...) { return Optional.of(onePixelLadder); }
 * }
 * }</pre>
 *
 * <p>Selecting 1.8.9 loads every change whose {@code vanillaChangedIn} is newer
 * than 1.8.9. If the same mechanic changed again later, only the change closest
 * to 1.8.9 is used.
 */
package me.wolfii.legacyparkourcompat.change;
