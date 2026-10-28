package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaPresenceHook;
import net.minecraft.world.entity.Entity;
import java.util.Optional;

/** Retains native lava-presence behavior until the accepted 1.16.1 latch boundary. */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeLavaPresence implements LavaPresenceHook {
    public NativeLavaPresence() {
    }

    @Override
    public Optional<Boolean> isInLava(Entity entity, Optional<LavaMaterialScanUpperBoundHook> scanUpperBound) {
        return Optional.empty();
    }
}
