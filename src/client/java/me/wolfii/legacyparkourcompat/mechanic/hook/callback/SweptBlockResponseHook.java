package me.wolfii.legacyparkourcompat.mechanic.hook.callback;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

/**
 * Supplies one eligible block callback response in the historical synchronous sweep.
 * The caller owns candidates, eligibility, onInsideBlock, summary insertion and finalization.
 */
@MechanicType("entity.swept-block-response")
public interface SweptBlockResponseHook extends VersionedMechanic {
    /**
     * @return true when this historical family response replaced the native callback;
     *         false leaves the native callback untouched.
     */
    boolean apply(BlockState state, Level level, BlockPos pos, Entity entity, VanillaCall nativeCallback);

    /**
     * Historical 1.21.2/1.21.3 Mojmap Entity.checkInsideBlocks dispatches block callbacks,
     * never WaterFluid/LavaFluid.entityInside; 26.2 unobfuscated Entity.checkInsideBlocks
     * dispatches FluidState.entityInside as well. The old-profile sweep must omit that call.
     */
    boolean omitSweptFluidCallback(FluidState state);
}
