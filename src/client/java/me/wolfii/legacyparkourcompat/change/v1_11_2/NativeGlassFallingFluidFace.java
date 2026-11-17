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

import java.util.List;

/**
 * Ornithe Feather 1.11.2 LiquidBlock.getFlow/isFaceSolid admits solid glass
 * material. Ornithe Feather 1.12 rejects full glass through the attachment
 * exception in the same side-face query. On this side of the boundary the
 * 26.2 unobfuscated FlowingFluid.isSolidFace result stays native.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class NativeGlassFallingFluidFace implements FallingFluidFaceSturdyHook {
    private final String blockId;

    private NativeGlassFallingFluidFace(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<NativeGlassFallingFluidFace> variants() {
        return FULL_GLASS_IDS.stream().map(NativeGlassFallingFluidFace::new).toList();
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
