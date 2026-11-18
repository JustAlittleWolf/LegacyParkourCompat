package me.wolfii.legacyparkourcompat.change.v1_11_2;

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

import java.util.ArrayList;
import java.util.List;

/**
 * In 1.9 through 1.11.2 Ornithe Feather, LiquidBlock.isFaceSolid delegates the ordinary
 * side check to Block.isFaceSolid, which accepts the registered fence materials
 * WOOD and STONE. In 1.12 Ornithe Feather, LiquidBlock.isFaceSolid requires a SOLID
 * face, and FenceBlock.getFaceShape returns MIDDLE_POLE for horizontal faces.
 * LiquidBlock.getFlow in both releases tests the side cell and the one above it.
 * The additional default-state sibling keys use the same face cue. See
 * development/research/source-evidence/20261118/block-a-1112-falling-fence-siblings/
 * and block-a-mid-nether-brick-falling-face/ in the same dated directory.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class FenceFallingFluidFace implements FallingFluidFaceSturdyHook {
    private final String blockId;
    private final boolean defaultStateOnly;

    private FenceFallingFluidFace(String blockId, boolean defaultStateOnly) {
        this.blockId = blockId;
        this.defaultStateOnly = defaultStateOnly;
    }

    @MovementChange.Factory
    public static List<FenceFallingFluidFace> variants() {
        var variants = new ArrayList<FenceFallingFluidFace>();
        variants.add(new FenceFallingFluidFace("minecraft:oak_fence", false));
        for (String blockId : DEFAULT_FENCE_IDS) {
            variants.add(new FenceFallingFluidFace(blockId, true));
        }
        return variants;
    }

    @Override
    public String variant() {
        return this.blockId;
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                               BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        if (fluid.isSame(Fluids.WATER) && direction.getAxis().isHorizontal()
            && (!this.defaultStateOnly || state == state.getBlock().defaultBlockState())
            && state.getFluidState().isEmpty() && !player.getAbilities().flying && !player.isPassenger()) {
            return true;
        }
        return vanilla.get();
    }
}
