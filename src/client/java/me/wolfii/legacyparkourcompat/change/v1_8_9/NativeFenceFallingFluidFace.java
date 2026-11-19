package me.wolfii.legacyparkourcompat.change.v1_8_9;

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

/** Preserves existing fallback for the default-state fence keys before the reviewed 1.9 route. */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class NativeFenceFallingFluidFace implements FallingFluidFaceSturdyHook {
    private final String blockId;

    private NativeFenceFallingFluidFace(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<NativeFenceFallingFluidFace> variants() {
        return DEFAULT_FENCE_IDS.stream().map(NativeFenceFallingFluidFace::new).toList();
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
