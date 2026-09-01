package me.wolfii.legacyparkourcompat.mechanic;

/**
 * Lets a behaviour invoke the untouched vanilla method it is wrapping.
 */
@FunctionalInterface
public interface VanillaCall {
    void run();
}
