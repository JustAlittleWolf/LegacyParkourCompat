package me.wolfii.legacyparkourcompat.change.v1_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PistonMovementHook;

/**
 * Pass piston requests directly into ordinary movement, including zero requests,
 * without accumulator writes or the limiter's early return. Caller geometry is
 * independent of this input limiter.
 *
 * <p>Exact sources: Minecraft 1.10, 1.10.1 and 1.10.2, Ornithe Feather
 * feather-gen2-&lt;version&gt;+build.2, net.minecraft.entity.Entity#move(double,double,double),
 * and Minecraft 1.11, feather-gen2-1.11+build.2,
 * Entity#move(MoverType,double,double,double), enter ordinary movement after the
 * noClip branch without a piston limiter. Minecraft 1.11.1,
 * feather-gen2-1.11.1+build.2, the same Entity#move, first introduces the
 * per-world-time signed axis accumulator, clamp to [-0.51, 0.51] and early return;
 * Minecraft 1.11.2, feather-gen2-1.11.2+build.2, retains that prefix unchanged.
 * The existing operation's coverage of older profiles is retained.
 *
 * <p>Minecraft 26.2, unobfuscated, net.minecraft.world.entity.Entity#move(MoverType,Vec3)
 * calls Entity#limitPistonMovement(Vec3) and returns for a zero result in its
 * first PISTON branch after noPhysics. Bypass the whole branch so zero requests
 * still reach ordinary movement and pistonDeltas/pistonDeltasGameTime stay
 * untouched. The later PISTON stuck-speed gate remains native.
 */
@MovementChange(emulates = ParkourVersion.V1_11)
public final class PistonMovement implements PistonMovementHook {
    @Override
    public boolean limitPistonMovement() {
        return false;
    }
}
