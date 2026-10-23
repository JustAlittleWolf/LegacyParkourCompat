package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FallingFluidFaceSturdyHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * In Mojmap 1.19.4 and 1.20/1.20.1, ChorusFlowerBlock has no
 * getBlockSupportShape override, so BlockBehaviour.getBlockSupportShape exposes
 * its full collision cube to SupportType.FULL. Mojmap 1.20.2 adds
 * ChorusFlowerBlock.BLOCK_SUPPORT_SHAPE and getBlockSupportShape, returning
 * box(1, 0, 1, 15, 15, 15); FULL then rejects horizontal faces. The V1_20 hook
 * restores the old horizontal sturdy answer for profiles through 1.20.1.
 * FlowingFluid.getFlow/isSolidFace retains its falling-fluid horizontal scan,
 * same-fluid and IceBlock gates, and normalize/add(0, -6, 0) response.
 *
 * <p>26.2 unobfuscated FlowingFluid.getFlow/isSolidFace still calls
 * BlockState.isFaceSturdy(SupportType.FULL); EntityFluidInteraction.update calls
 * FluidState.getFlow for the local player's accumulated current. The mixins scope
 * this predicate to that call and retain all native flow accumulation and gates.
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class ChorusFlowerFallingFluidFace implements FallingFluidFaceSturdyHook {
    @Override
    public String variant() {
        return "minecraft:chorus_flower";
    }

    @Override
    public boolean isFaceSturdy(BlockState state, BlockGetter level, BlockPos pos, Direction direction,
                                VanillaFn<Boolean> vanilla) {
        return direction.getAxis().isHorizontal() || vanilla.get();
    }
}
