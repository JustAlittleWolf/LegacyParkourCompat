package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FallingFluidFaceSturdyHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.List;

/**
 * Preserves the native face predicate before the proven 1.13 stair behavior.
 * The same 17 stair keys are handled historically from V1_13_1 onward; the
 * earlier exact-source range has not been established by this finding.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeStairFluidFace implements FallingFluidFaceSturdyHook {
    private final String blockId;

    private NativeStairFluidFace(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<NativeStairFluidFace> variants() {
        return V1_13_STAIR_IDS.stream().map(NativeStairFluidFace::new).toList();
    }

    @Override
    public String variant() {
        return this.blockId;
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        return vanilla.get();
    }
}
