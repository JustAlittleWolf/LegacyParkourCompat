package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCallbackAxisOrderHook;
import net.minecraft.world.phys.Vec3;

/**
 * Mojmap 1.21.6, 1.21.7 and 1.21.8 Entity.checkInsideBlocks(List,
 * StepBasedCollector) order axis-separated segments with to.subtract(from).
 * Mojmap 1.21.9 Entity.move instead records the requested displacement after
 * maybeBackOffFromEdge and before collide; checkInsideBlocks uses that vector
 * for axis order. Unobfuscated 26.2 Entity.move/checkInsideBlocks retains the
 * latter operand. Restore only the ordering operand, including X before Z on
 * equal magnitudes, through the independently resolved 1.21.6–1.21.8 profile.
 *
 * This does not restore movement-record construction/compression, the old
 * BlockGetter.addCollisionsAlongTravel loop counter, the later per-record
 * traversal budget, or the callback precision flag. Those are separate
 * operations; this change does not establish complete callback traversal parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class BlockCallbackAxisOrder implements BlockCallbackAxisOrderHook {
    @Override
    public Vec3 callbackAxisOrder(Vec3 recordedMovement, Vec3 resolvedMovement) {
        return resolvedMovement;
    }
}
