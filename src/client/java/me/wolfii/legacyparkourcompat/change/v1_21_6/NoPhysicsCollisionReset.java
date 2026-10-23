package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.NoPhysicsCollisionResetHook;

/**
 * Minecraft 1.21.6, 1.21.7 and 1.21.8 Mojmap,
 * net.minecraft.world.entity.Entity#move(MoverType, Vec3): the noPhysics branch
 * only calls setPos, leaving horizontalCollision, verticalCollision,
 * verticalCollisionBelow and minorHorizontalCollision unchanged.
 * Minecraft 1.21.9 and 1.21.10 Mojmap, same class/member, add four false writes
 * after setPos; Minecraft 26.2 unobfuscated, same class/member, retains them.
 * Player#tick assigns noPhysics from isSpectator in both historical releases.
 * LocalPlayer#aiStep consults shouldStopRunSprinting on the next step: its
 * horizontalCollision && !minorHorizontalCollision predicate can then cancel
 * sprinting. Player#getFlyingSpeed doubles the flying speed while sprinting.
 * This collision/sprint dependency exists in 1.21.8 and 1.21.10 Mojmap and
 * their unobfuscated 26.2 counterparts.
 * Skipping those writes preserves flags at that operation, including any effects
 * of setPos, without restoring an earlier snapshot or changing colliding movement.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class NoPhysicsCollisionReset implements NoPhysicsCollisionResetHook {
    @Override
    public boolean clearCollisionFlags() {
        return false;
    }
}
