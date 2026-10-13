package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.KeyboardDiagonalInputHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInputVector.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.20.6/mojmap/net/minecraft/world/phys/Vec3.java:
 *   normalize.
 * 1.21.11/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   modifyInput.
 * 1.21.11/mojmap/net/minecraft/util/Mth.java:
 *   class declaration / field initialization.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInputVector.
 * 1.21.11/mojmap/net/minecraft/world/phys/Vec2.java:
 *   normalized.
 * 1.21.4/mojmap/net/minecraft/client/player/ClientInput.java:
 *   class declaration / field initialization.
 * 1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   serverAiStep.
 * 1.21.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInputVector.
 * 1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   modifyInput.
 *   applyInput.
 * 1.21.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInputVector.
 * 1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.21.5/mojmap/net/minecraft/world/phys/Vec2.java:
 *   normalized.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class KeyboardDiagonalInput implements KeyboardDiagonalInputHook {
    @Override
    public Vec2 diagonalInput(
        Player player,
        Vec2 rawImpulses,
        Vec2 vanilla,
        boolean keyboard,
        boolean diagonal,
        boolean movingSlowly,
        boolean usingItem
    ) {
        if (keyboard && diagonal && !movingSlowly && !usingItem) {
            return rawImpulses.scale(0.98F);
        }
        return vanilla;
    }
}
