package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/** Loaded-chunk admission before sampling the player's fluid interaction box. */
@MechanicType("player.fluid.scan_loaded_chunks")
public interface FluidScanLoadedChunksHook extends VersionedMechanic {
    Optional<Boolean> scanAllowed(Player player, ParkourVersion selected);
}
