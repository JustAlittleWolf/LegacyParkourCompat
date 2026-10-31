package me.wolfii.legacyparkourcompat.change.v1_13_1;

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
import net.minecraft.world.level.material.WaterFluid;

import java.util.List;

/**
 * In 1.13/1.13.1/1.13.2 Ornithe Feather FlowingFluid#getFlow invokes
 * #isSolidFace, which rejects StairsBlock before checking its face shape.
 * 1.14.4 uses collision-face solidity on the same route and can admit the
 * matching stair. Only the 17 stairs registered by the 1.13 source are keyed.
 * The player-current evidence is for water; other fluid queries retain native behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class LegacyStairFluidFace implements FallingFluidFaceSturdyHook {
    private final String blockId;

    private LegacyStairFluidFace(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<LegacyStairFluidFace> variants() {
        return V1_13_STAIR_IDS.stream().map(LegacyStairFluidFace::new).toList();
    }

    @Override
    public String variant() {
        return this.blockId;
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        return fluid instanceof WaterFluid && direction.getAxis().isHorizontal() ? false : vanilla.get();
    }
}
