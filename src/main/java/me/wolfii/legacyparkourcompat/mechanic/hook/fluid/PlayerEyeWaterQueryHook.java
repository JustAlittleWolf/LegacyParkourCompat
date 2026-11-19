package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.function.DoubleSupplier;

/** Supplies the historical point query used to produce the player's cached underwater value. */
@MechanicType("player.eye_water_query")
public interface PlayerEyeWaterQueryHook extends VersionedMechanic {
    /** Samples the supplied eye point after vehicle checks, or returns empty to keep native behavior. */
    Optional<Boolean> queryEyeWater(Player player, DoubleSupplier eyeSampleY);
}
