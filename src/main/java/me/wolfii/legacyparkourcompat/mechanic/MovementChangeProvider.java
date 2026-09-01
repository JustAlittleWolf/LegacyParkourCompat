package me.wolfii.legacyparkourcompat.mechanic;

/**
 * Fabric entrypoint {@code legacyparkourcompat:movement-change}.
 *
 * <p>Later agents add implementations here without touching mixins. Use one
 * package per {@link me.wolfii.legacyparkourcompat.api.ParkourVersion} (for
 * example {@code change.v1_8}):
 * <pre>{@code
 * // change.v1_8.MovementChanges
 * public final class MovementChanges implements MovementChangeProvider {
 *     public void register(MovementChangeRegistry registry) {
 *         registry.register(new LadderCollision());
 *     }
 * }
 * }</pre>
 */
public interface MovementChangeProvider {
    void register(MovementChangeRegistry registry);
}
