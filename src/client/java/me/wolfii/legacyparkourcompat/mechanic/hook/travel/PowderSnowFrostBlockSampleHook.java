package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

/** Selects the block state used by the local frost slowdown gate. */
@MechanicType("player.powder_snow.frost_block_sample")
public interface PowderSnowFrostBlockSampleHook extends VersionedMechanic {
    BlockState sample(Player player);
}
