package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Replays the historical grounded step callback at its per-move stage. */
@MechanicType("entity.step_on_callback")
public interface StepOnCallbackHook extends VersionedMechanic {
    void invokeBeforeInsideBlockCallbacks(Player player, Level level, BlockPos position, BlockState state);
}
