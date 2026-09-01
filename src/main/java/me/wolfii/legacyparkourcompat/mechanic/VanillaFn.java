package me.wolfii.legacyparkourcompat.mechanic;

/**
 * Lets a behaviour wrap a vanilla method that returns a value.
 */
@FunctionalInterface
public interface VanillaFn<T> {
    T get();
}
