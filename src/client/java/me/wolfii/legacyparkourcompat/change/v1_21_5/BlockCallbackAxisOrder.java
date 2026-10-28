package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCallbackAxisOrderHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * Mojmap 1.21.5 Entity.move orders its pre-split segments from resolved
 * displacement. The movement-record adapter uses this resolved operand when
 * constructing the source-ordered per-move batch.
 * Unobfuscated 26.2 Entity.checkInsideBlocks uses the recorded requested vector.
 * Earlier final-box contact replay remains independently resolved.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class BlockCallbackAxisOrder implements BlockCallbackAxisOrderHook {
    @Override
    public boolean appliesTo(Entity entity) {
        return entity instanceof LocalPlayer && entity.level().isClientSide()
            && !entity.isRemoved() && !entity.noPhysics;
    }

    @Override
    public Vec3 callbackAxisOrder(Vec3 recordedMovement, Vec3 resolvedMovement) {
        return resolvedMovement;
    }
}
