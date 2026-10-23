package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.NoPhysicsCollisionResetHook;

/**
 * Scope boundary for the independently accepted 1.21.6-1.21.8 change.
 * Keep earlier selections native rather than extending that accepted range.
 * Minecraft 26.2 unobfuscated, net.minecraft.world.entity.Entity#move(MoverType,
 * Vec3), clears all four collision flags after setPos in its noPhysics branch;
 * Minecraft 1.21.8 Mojmap, same class/member, omits those writes.
 * This boundary makes no historical-behavior claim about 1.21.5 or earlier.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class NativeNoPhysicsCollisionReset implements NoPhysicsCollisionResetHook {
    @Override
    public boolean clearCollisionFlags() {
        return true;
    }
}
