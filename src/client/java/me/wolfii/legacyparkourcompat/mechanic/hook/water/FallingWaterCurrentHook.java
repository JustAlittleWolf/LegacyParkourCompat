package me.wolfii.legacyparkourcompat.mechanic.hook.water;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Function;

/** Historical per-cell response for a supported falling state at the legacy upper endpoint. */
@MechanicType("water.current.falling_endpoint")
public interface FallingWaterCurrentHook extends VersionedMechanic {
    record Sample(Vec3 flow, BlockPos zeroFlowCompanion) { }

    /** The horizontal callback returns the normalized flow for a supplied center state. */
    Optional<Sample> sample(Level level, BlockPos pos, BlockState state,
                            Function<BlockState, Optional<Vec3>> horizontalFlow);
}
