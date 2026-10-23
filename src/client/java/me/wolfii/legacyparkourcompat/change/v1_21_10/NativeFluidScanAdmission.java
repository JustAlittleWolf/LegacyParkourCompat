package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidScanLoadedChunksHook;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * Applicability boundary, not a claim about 1.21.10 physics. The accepted
 * 1.21.11 Mojmap Entity.touchingUnloadedChunk / LevelReader.hasChunksAt /
 * ClientLevel.hasChunk evidence against 26.1 and 26.2 published unobfuscated
 * EntityFluidInteraction.update / hasFluidAndLoaded does not establish earlier
 * profiles. Retain native admission there; the existing 1.16.2 change remains
 * the closer applicable change for its earlier profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class NativeFluidScanAdmission implements FluidScanLoadedChunksHook {
    @Override
    public Optional<Boolean> scanAllowed(Player player, ParkourVersion selected) {
        return Optional.empty();
    }
}
