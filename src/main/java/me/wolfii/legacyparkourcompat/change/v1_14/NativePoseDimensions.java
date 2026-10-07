package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/** Ends the older crouch and swim-fallback dimension overrides at the 1.14 boundary. */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class NativePoseDimensions implements PlayerDimensionsBehavior {
    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        return vanilla;
    }
}
