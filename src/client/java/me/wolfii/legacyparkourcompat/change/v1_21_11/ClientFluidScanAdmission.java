package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidScanLoadedChunksHook;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * 1.21.11 Mojmap: Entity.updateFluidHeightAndDoFluidPushing calls
 * Entity.touchingUnloadedChunk, whose LevelReader.hasChunksAt dispatches to
 * ClientLevel.hasChunk, which always returns true. The logical-client scan
 * therefore proceeds even with missing chunks. Level.getFluidState and
 * ClientChunkCache.getChunk use EmptyLevelChunk.getFluidState's EMPTY fallback
 * without loading or generating chunks.
 *
 * <p>26.1 and 26.2 published unobfuscated: EntityFluidInteraction.update calls
 * hasFluidAndLoaded, which queries FULL chunks with loadOrGenerate=false and
 * rejects any missing neighbor. Admit the existing scanner instead, preserving
 * its cell reads, body/eye sampling and current arithmetic. This operation does
 * not replace the separate historical eye comparison or boat mask.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ClientFluidScanAdmission implements FluidScanLoadedChunksHook {
    @Override
    public Optional<Boolean> scanAllowed(Player player, ParkourVersion selected) {
        return Optional.of(true);
    }
}
