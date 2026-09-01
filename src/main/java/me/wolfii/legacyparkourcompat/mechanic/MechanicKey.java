package me.wolfii.legacyparkourcompat.mechanic;

import java.util.Objects;

/**
 * Identity of one independently selectable mechanic.
 *
 * <p>The {@code type} is the mixin-free hook interface (for example
 * {@link me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape}).
 * {@code variant} distinguishes keyed mechanics such as per-block collision
 * ({@code minecraft:ladder} vs {@code minecraft:soul_sand}); it is empty for
 * singleton mechanics such as jump power.
 */
public record MechanicKey(Class<?> type, String variant) {
    public MechanicKey {
        Objects.requireNonNull(type, "type");
        variant = variant == null ? "" : variant;
    }

    public static MechanicKey of(Class<?> type) {
        return new MechanicKey(type, "");
    }

    public static MechanicKey of(Class<?> type, String variant) {
        return new MechanicKey(type, variant);
    }

    public String qualifiedId() {
        MechanicType annotation = this.type.getAnnotation(MechanicType.class);
        String base = annotation != null ? annotation.value() : this.type.getSimpleName();
        return this.variant.isEmpty() ? base : base + ":" + this.variant;
    }
}
