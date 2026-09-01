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
 * <p>The UI agent should call {@link #select(MinecraftVersion)} (or
 * {@link #select(String)}) when the player picks a version. Selecting
 * {@code 1.8.9} loads every registered change that vanilla introduced after
 * 1.8.9, and if the same mechanic changed more than once, only the change
 * closest to 1.8.9 is used.
 *
 * <p>Default is the native game version, which applies no historical deltas.
 */
public interface MovementController {
    static MovementController get() {
        return MovementControllerImpl.get();
    }

    MinecraftVersion nativeVersion();

    MinecraftVersion selectedVersion();

    /**
     * Per-player override, falling back to {@link #selectedVersion()}.
     */
    MinecraftVersion versionFor(@Nullable Entity entity);

    void select(MinecraftVersion version);

    default void select(String versionId) {
        this.select(MinecraftVersion.parse(versionId));
    }

    /**
     * Optional per-player override used on dedicated servers. Pass {@code null}
     * to clear the override.
     */
    void selectFor(UUID playerId, @Nullable MinecraftVersion version);

    default void selectFor(Player player, @Nullable MinecraftVersion version) {
        this.selectFor(player.getUUID(), version);
    }

    List<MinecraftVersion> suggestedVersions();

    ActiveMovementProfile profile();

    ActiveMovementProfile profileFor(@Nullable Entity entity);

    MovementChangeRegistry registry();

    void addListener(MovementVersionListener listener);

    void removeListener(MovementVersionListener listener);
}
