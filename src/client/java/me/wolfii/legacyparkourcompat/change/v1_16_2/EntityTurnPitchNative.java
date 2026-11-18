package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.turn.EntityTurnPitchHook;
import net.minecraft.world.entity.Entity;

/**
 * Keeps the ordinary turn pitch response native through 1.16.5. Mojmap 1.16.5
 * {@code Entity.turn(double,double)} updates and clamps its pitch field
 * directly; the 1.17 {@code Entity.turn(double,double)} / {@code
 * Entity.setXRot(float)} path starts the separately bounded source range.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class EntityTurnPitchNative implements EntityTurnPitchHook {
    @Override
    public float transformFirstPitchArgument(Entity entity, float vanillaArgument) {
        return vanillaArgument;
    }
}
