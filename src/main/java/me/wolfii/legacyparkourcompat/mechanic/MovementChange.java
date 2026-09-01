package me.wolfii.legacyparkourcompat.mechanic;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a historical movement delta.
 *
 * <p>{@link #vanillaChangedIn()} is the first Minecraft version whose vanilla
 * code no longer matches this implementation. The change is applied when the
 * selected version is <em>older</em> than that. If several deltas exist for the
 * same mechanic, the one whose {@code vanillaChangedIn} is closest to (but still
 * newer than) the selection wins.
 *
 * <p>Example from {@code AGENTS.md}: mechanic {@code A.A} changed in 1.9
 * (Change A) and 1.11 (Change C). Selecting 1.8 loads Change A, not Change C.
 *
 * <p>Ladder boxes that vanilla replaced in 1.9:
 * <pre>{@code
 * @MovementChange(vanillaChangedIn = "1.9", emulates = "1.8")
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
     * First vanilla version that replaced this behaviour. Applied when the
     * selected version is older than this id.
     */
    String vanillaChangedIn();

    /**
     * Documentation only: the version this implementation emulates (for example
     * {@code 1.8}). Not used when resolving which change to load.
     */
    String emulates() default "";
}
