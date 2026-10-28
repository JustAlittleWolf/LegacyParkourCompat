package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackRecordHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

/** Captures the uncompressed 1.21.5 axis-split segment batch. */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class MovementCallbackRecord implements MovementCallbackRecordHook {
    @Override
    public boolean suppressMovementRecord(Entity entity) {
        return entity instanceof LocalPlayer && entity.level().isClientSide()
            && !entity.isRemoved() && !entity.noPhysics;
    }

    @Override
    public boolean captureAxisSegments(Entity entity) {
        return entity instanceof LocalPlayer && entity.level().isClientSide()
            && !entity.isRemoved() && !entity.noPhysics;
    }
}
