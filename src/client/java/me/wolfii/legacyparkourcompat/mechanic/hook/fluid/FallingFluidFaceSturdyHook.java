package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

/** FULL-face support predicate used by a local player's falling-fluid current query. */
@MechanicType("fluid.falling_face_sturdy")
public interface FallingFluidFaceSturdyHook extends VersionedMechanic {
    boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                         BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla);
}
