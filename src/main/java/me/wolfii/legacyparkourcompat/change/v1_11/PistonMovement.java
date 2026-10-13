package me.wolfii.legacyparkourcompat.change.v1_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PistonMovementHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_11)
public final class PistonMovement implements PistonMovementHook {
    @Override
    public Vec3 limitPistonMovement(Player player, Vec3 vanillaMovement) {
        return vanillaMovement;
    }
}
