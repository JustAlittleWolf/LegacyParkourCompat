package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.turn.EntityTurnPitchHook;
import net.minecraft.world.entity.Entity;

/** Keeps the first ordinary mouse-turn pitch setter argument native before the 1.20.5 boundary. */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class EntityTurnPitchNative implements EntityTurnPitchHook {
    @Override
    public float transformFirstPitchArgument(Entity entity, float vanillaArgument) {
        return vanillaArgument;
    }
}
