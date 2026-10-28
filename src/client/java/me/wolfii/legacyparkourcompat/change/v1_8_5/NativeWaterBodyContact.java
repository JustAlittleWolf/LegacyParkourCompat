package me.wolfii.legacyparkourcompat.change.v1_8_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Keeps body-contact replacement on native fallback outside the accepted 1.8.9 scope.
 * This is not a claim that earlier profiles match the modern native result.
 */
@MovementChange(emulates = ParkourVersion.V1_8_5)
public final class NativeWaterBodyContact implements WaterBodyContactHook {
    public NativeWaterBodyContact() {
    }

    @Override
    public Optional<AABB> probe(AABB body) {
        // The accepted scope is the 1.8.9/1.9 Feather comparison of
        // Entity.checkWaterCollisions and World.applyLiquidDrag; V1_8_5 remains native.
        return Optional.empty();
    }

    @Override
    public boolean includes(int exclusiveMaxY, int cellY, int liquidLevel) {
        return false;
    }
}
