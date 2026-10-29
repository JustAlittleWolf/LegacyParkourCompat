package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/**
 * Selects the historical client player-chunk readiness predicate.
 *
 * <p>The probe methods are deliberately lazy. Implementations must bind
 * {@link Probe#outsideBuildHeight()} and the section query to the same captured
 * player block position for the synchronous historical paths. The renderer
 * query methods represent their complete vanilla predicates, including mesh
 * compilation and, where applicable, visibility.
 */
@MechanicType("player.chunk_readiness")
public interface PlayerChunkReadinessHook extends VersionedMechanic {
    boolean isReady(Probe probe);

    /** Client-side observations consumed only when the selected path reaches them. */
    interface Probe {
        /** Native readiness result, used by the lower native fence. */
        boolean nativeReady();

        /** Whether the player's block position is outside the level build height. */
        boolean outsideBuildHeight();

        /** The renderer's compiled-section result for the player's block position. */
        boolean playerSectionCompiled();

        /** Whether the player is a spectator. */
        boolean spectator();

        /** Whether the player is alive. */
        boolean alive();

        /** Current value returned by the source's millisecond clock. */
        long currentTimeMillis();

        /** Deadline stored by the client's level-load tracker. */
        long timeoutAfterMillis();

        /** The renderer's compiled-and-visible result for the player's block position. */
        boolean playerSectionCompiledAndVisible();
    }
}
