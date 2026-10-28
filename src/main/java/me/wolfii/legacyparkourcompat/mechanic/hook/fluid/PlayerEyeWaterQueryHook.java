package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/** Supplies the historical point query used to produce the player's cached underwater value. */
@MechanicType("player.eye_water_query")
public interface PlayerEyeWaterQueryHook extends VersionedMechanic {
    /** Returns the historical point-query result, or empty for a modern vehicle without a 1.13 equivalent. */
    Optional<Boolean> queryEyeWater(Player player);
}
