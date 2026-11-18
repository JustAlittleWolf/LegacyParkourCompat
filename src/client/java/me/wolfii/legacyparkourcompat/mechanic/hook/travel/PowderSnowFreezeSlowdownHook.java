package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

/** Recomputes the movement-speed modifier at the historical post-travel point. */
@MechanicType("player.powder_snow.freeze_slowdown")
public interface PowderSnowFreezeSlowdownHook extends VersionedMechanic {
    void recompute(Player player, VanillaFn<BlockState> frostSample);
}
