package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.movement.HeadingVelocityHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Keeps modern native movement after the 1.8.9 heading arithmetic boundary. */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class NativeHeadingVelocity implements HeadingVelocityHook {
    @Override
    public boolean apply(Player player, float speed, Vec3 input) {
        return false;
    }
}
