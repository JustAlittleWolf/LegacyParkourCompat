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
 * <p>The UI should list {@link #selectableVersions()} (or {@link #suggestedVersions()}).
 * Patch releases with the same parkour mechanics share one version: selecting
 * {@code 1.9} also covers {@code 1.9.1} and {@code 1.9.2}.
 *
 * <p>The vanilla/disabled version (native game version, or {@link #disable()})
 * applies no historical deltas. Mixins become no-ops, so Minecraft mechanics
 * are unchanged. Switching versions while a world is loaded takes effect on the
 * next player tick, including collision used for movement. Outline/cosmetic
 * block boxes are not modified.
 */
public interface MovementController {
    static MovementController get() {
        return MovementControllerImpl.get();
    }

    MinecraftVersion nativeVersion();

    /**
     * Canonical parkour version currently selected. {@link ParkourVersion#isVanilla()}
     * means latest / disabled.
     */
    ParkourVersion selectedParkourVersion();

    /**
     * Resolution target of the selected version (the version id, or native when disabled).
     */
    MinecraftVersion selectedVersion();

    /**
     * {@code true} when historical movement is applied. {@code false} when the
     * native/disabled version is selected.
     */
    boolean isEnabled();

    boolean isEnabled(@Nullable Entity entity);

    /**
     * Per-player version, falling back to {@link #selectedParkourVersion()}.
     */
    ParkourVersion parkourVersionFor(@Nullable Entity entity);

    MinecraftVersion versionFor(@Nullable Entity entity);

    void select(ParkourVersion version);

    default void select(MinecraftVersion version) {
        this.select(ParkourVersions.of(version));
    }

    default void select(String versionId) {
        this.select(ParkourVersions.of(versionId));
    }

    /**
     * Restore native Minecraft movement. Same as selecting the latest version.
     */
    default void disable() {
        this.select(ParkourVersions.vanilla());
    }

    /**
     * Optional per-player override used on dedicated servers. Pass {@code null}
     * to clear the override.
     */
    void selectFor(UUID playerId, @Nullable ParkourVersion version);

    default void selectFor(UUID playerId, @Nullable MinecraftVersion version) {
        this.selectFor(playerId, version == null ? null : ParkourVersions.of(version));
    }

    default void selectFor(Player player, @Nullable ParkourVersion version) {
        this.selectFor(player.getUUID(), version);
    }

    default void selectFor(Player player, @Nullable MinecraftVersion version) {
        this.selectFor(player.getUUID(), version);
    }

    List<ParkourVersion> selectableVersions();

    /**
     * Version ids for UIs that want a flat list. The last entry is native
     * (disabled). {@code 1.9.2} is not listed separately from {@code 1.9}.
     */
    default List<MinecraftVersion> suggestedVersions() {
        return ParkourVersions.suggested();
    }

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
