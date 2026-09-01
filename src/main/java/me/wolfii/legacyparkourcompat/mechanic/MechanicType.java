package me.wolfii.legacyparkourcompat.mechanic;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a mixin-free movement hook. Implement this interface from a
 * {@link MovementChange}-annotated class; do not write mixins.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MechanicType {
    /**
     * Stable id used in logs and profiles, for example {@code block.collision}
     * or {@code player.jump}.
     */
    String value();
}
