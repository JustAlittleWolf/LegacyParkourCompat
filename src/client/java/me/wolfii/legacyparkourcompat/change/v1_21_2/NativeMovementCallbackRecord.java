package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackRecordHook;
import net.minecraft.world.entity.Entity;

/** Keeps native movement callback record creation before the 1.21.4 cutover. */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativeMovementCallbackRecord implements MovementCallbackRecordHook {
    @Override
    public boolean suppressMovementRecord(Entity entity) {
        return false;
    }
}
