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
import net.minecraft.world.level.material.Fluids;

import java.util.List;

/**
 * Ornithe Feather 1.12–1.12.2 LiquidBlock.getFlow/isFaceSolid and 1.13–1.13.2
 * FlowingFluid.getFlow/isSolidFace exclude full glass via the attachment
 * exception for the horizontal neighbor and its upper cell. Ornithe Feather
 * 1.14 uses collision-face support and admits those full glass faces again.
 * The 26.2 unobfuscated FlowingFluid falling-current scan owns both probes.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class GlassFallingFluidFace implements FallingFluidFaceSturdyHook {
    private final String blockId;

    private GlassFallingFluidFace(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<GlassFallingFluidFace> variants() {
        return FULL_GLASS_IDS.stream().map(GlassFallingFluidFace::new).toList();
    }

    @Override
    public String variant() {
        return this.blockId;
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        if (fluid.isSame(Fluids.WATER) && direction.getAxis().isHorizontal()) {
            return false;
        }
        return vanilla.get();
    }
}
