package me.wolfii.legacyparkourcompat.mechanic;

/**
 * Fabric entrypoint {@code legacyparkourcompat:movement-change}.
 *
 * <p>Later agents add implementations here without touching mixins:
 * <pre>{@code
 * public final class LadderChanges implements MovementChangeProvider {
 *     public void register(MovementChangeRegistry registry) {
 *         registry.register(new LadderCollision_1_8());
 *     }
 * }
 * }</pre>
 */
public interface MovementChangeProvider {
    void register(MovementChangeRegistry registry);
}
