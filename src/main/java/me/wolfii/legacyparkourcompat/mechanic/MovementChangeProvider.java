package me.wolfii.legacyparkourcompat.mechanic;

/**
 * Fabric entrypoint {@code legacyparkourcompat:movement-change}.
 *
 * <p>Fresh implementations register providers here. Providers may group
 * changes by emulated version, but the registration contract stays generic:
 * <pre>{@code
 * // ExampleMovementChanges
 * public final class MovementChanges implements MovementChangeProvider {
 *     public void register(MovementChangeRegistry registry) {
 *         registry.register(new ExampleMovementChange());
 *     }
 * }
 * }</pre>
 */
public interface MovementChangeProvider {
    void register(MovementChangeRegistry registry);
}
