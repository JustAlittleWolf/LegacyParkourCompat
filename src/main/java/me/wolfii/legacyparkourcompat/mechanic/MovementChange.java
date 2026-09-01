package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
 * public final class LadderCollision_1_8 implements BlockCollisionShape {
 *     public String blockId() { return "minecraft:ladder"; }
 *     public Optional<VoxelShape> collisionShape(...) { return Optional.of(...); }
 * }
 * }</pre>
 *
 * <p>The implementing class must not reference mixins. Register it from a
 * {@link MovementChangeProvider} or {@link MovementChangeRegistry#register(Object)}.
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
}
