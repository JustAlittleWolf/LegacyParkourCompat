package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightSneakInputHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/**
 * Feather
 * {@code 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java#mobTick}
 * leaves the keyboard-scaled axes unchanged in its flight branch; Feather
 * {@code 1.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java#mobTick}
 * and
 * {@code 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java#mobTick}
 * add stored-axis division. Mojmap
 * {@code 1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java#aiStep}
 * retains the division, while
 * {@code 1.15/mojmap/net/minecraft/client/player/LocalPlayer.java#aiStep}
 * removes it, so the resolver boundary is V1_14_2.
 * Yarn 1.14.2+build.7-v2 and 1.14.3+build.13-v2,
 * ClientPlayerEntity.tickMovement, retain the same double division and
 * flying/controlled-camera/current-sneak gates; Mojmap 1.15.1 and 1.15.2,
 * LocalPlayer.aiStep, retain its removal.
 *
 * This runs after KeyboardInput.tick and LocalPlayer.aiStep's item-use
 * scaling, before LivingEntity.aiStep damps movement copies by 0.98F.
 * Keep the axes unnormalized until Entity.getInputVector: the spectator
 * exclusion in KeyboardInput.tick skips scaling but does not skip this
 * division. A single spectator axis therefore exceeds one after damping
 * and normalizes to one during travel; without division it remains 0.98.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class FlightSneakCompensationV1_14_2 implements FlightSneakInputHook {
    @Override
    public Vec2 compensate(Player player, ParkourVersion selected, Vec2 storedInput) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return storedInput;
        }
        return new Vec2((float)(storedInput.x / 0.3), (float)(storedInput.y / 0.3));
    }
}
