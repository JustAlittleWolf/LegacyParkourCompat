package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/** Keeps 1.9 body contact native, after the bounded 1.8.0-1.8.9 range. */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class NativeWaterBodyContact implements WaterBodyContactHook {
    public NativeWaterBodyContact() {
    }

    @Override
    public Optional<AABB> probe(AABB body) {
        // The accepted comparison is 1.8.9 and 1.9 Feather
        // Entity.checkWaterCollisions/World.applyLiquidDrag; this provider leaves
        // V1_9+ contact native while the bound hook falls back to native ceil.
        return Optional.empty();
    }

    @Override
    public boolean includes(int exclusiveMaxY, int cellY, int liquidLevel) {
        return false;
    }
}
