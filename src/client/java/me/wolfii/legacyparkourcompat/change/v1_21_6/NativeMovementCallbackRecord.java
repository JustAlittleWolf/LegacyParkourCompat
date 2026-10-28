package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackRecordHook;
import net.minecraft.world.entity.Entity;

/** Keeps the native 100-record compression introduced with the 1.21.6 deque model. */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class NativeMovementCallbackRecord implements MovementCallbackRecordHook {
    @Override
    public boolean suppressMovementRecord(Entity entity) {
        return false;
    }
}
