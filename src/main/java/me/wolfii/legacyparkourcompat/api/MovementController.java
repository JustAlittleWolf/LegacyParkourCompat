package me.wolfii.legacyparkourcompat.api;

import me.wolfii.legacyparkourcompat.impl.MovementControllerImpl;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Public API for selecting the movement version and registering deltas.
 *
 * <p>The UI should list {@link #selectableVersions()}. Patch releases with the
 * same parkour mechanics share one {@link ParkourVersion}: selecting
 * {@link ParkourVersion#V1_9} also covers {@code 1.9.1} and {@code 1.9.2}.
 *
 * <p>{@link ParkourVersion#VANILLA} (or {@link #disable()}) applies no historical
 * deltas. Mixins become no-ops, so Minecraft mechanics are unchanged. Switching
 * versions while a world is loaded takes effect on the next player tick,
 * including collision used for movement. Outline/cosmetic block boxes are not
 * modified.
 */
public interface MovementController {
    static MovementController get() {
        return MovementControllerImpl.get();
    }

    /**
     * Parkour version of the running game. {@link ParkourVersion#VANILLA} when
     * the native release is the current latest.
     */
    ParkourVersion nativeVersion();

    ParkourVersion selectedVersion();

    /**
     * {@code true} when historical movement is applied. {@code false} when
     * {@link ParkourVersion#VANILLA} is selected (or the selection is the
     * running game).
     */
    boolean isEnabled();

    boolean isEnabled(@Nullable Entity entity);

    /**
     * Per-player version, falling back to {@link #selectedVersion()}.
     */
    ParkourVersion versionFor(@Nullable Entity entity);

    void select(ParkourVersion version);

    default void select(String versionId) {
        this.select(ParkourVersion.of(versionId));
    }

    /**
     * Restore native Minecraft movement. Same as selecting {@link ParkourVersion#VANILLA}.
     */
    default void disable() {
        this.select(ParkourVersion.VANILLA);
    }

    /**
     * Optional per-player override used on dedicated servers. Pass {@code null}
     * to clear the override.
     */
    void selectFor(UUID playerId, @Nullable ParkourVersion version);

    default void selectFor(Player player, @Nullable ParkourVersion version) {
        this.selectFor(player.getUUID(), version);
    }

    List<ParkourVersion> selectableVersions();

    ActiveMovementProfile profile();

    ActiveMovementProfile profileFor(@Nullable Entity entity);

    MovementChangeRegistry registry();

    /**
     * Incremented whenever the selected version or registered changes change, so
     * in-world players can refresh pose and dimensions on the next tick.
     */
    int epoch();

    void addListener(MovementVersionListener listener);

    void removeListener(MovementVersionListener listener);
}
