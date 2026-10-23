package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCallbackAxisOrderHook;
import net.minecraft.world.phys.Vec3;

/**
 * Mojmap 1.21.5 Entity.move pre-splits resolved movement into position records;
 * 1.21.6–1.21.8 Entity.checkInsideBlocks instead splits an accumulated record.
 * The accepted 1.21.6–1.21.8 ordering operand cannot restore the 1.21.5 record
 * construction. Retain the native operand through 1.21.5 as an explicit gap.
 * Unobfuscated 26.2 Entity.checkInsideBlocks uses the recorded requested vector.
 * Earlier final-box contact replay remains independently resolved.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class NativeBlockCallbackAxisOrder implements BlockCallbackAxisOrderHook {
    @Override
    public Vec3 callbackAxisOrder(Vec3 recordedMovement, Vec3 resolvedMovement) {
        return recordedMovement;
    }
}
