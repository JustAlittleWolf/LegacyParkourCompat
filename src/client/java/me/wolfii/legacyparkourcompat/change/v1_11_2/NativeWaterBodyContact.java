package me.wolfii.legacyparkourcompat.change.v1_11_2;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import net.minecraft.world.phys.AABB;

/** Earlier body/current scans have no accepted source binding in this integration. */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class NativeWaterBodyContact implements WaterBodyContactHook {
    public Optional<AABB> probe(AABB body) { return Optional.empty(); }
    public boolean includes(int exclusiveMaxY, int cellY, int liquidLevel) { return false; }
}
