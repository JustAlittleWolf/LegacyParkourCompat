package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightSneakInputHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   public void tick().
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   public void tick().
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-TICK-02-flight-sneak-input-rescaling is unknown within (1.8.9, 1.9.4]
 *   Exact independent source verdict/snapshot identity for TICK-02-flight-sneak-input-rescaling
 *   Exact independent source verdict/snapshot identity for MD-02-flight-sneak-input-rescaling
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class CreativeFlightSneakInput implements FlightSneakInputHook {
    @Override
    public Vec2 modify(Player player, Vec2 input, boolean slowedBySneaking) {
        boolean flyingSneak = player.getAbilities().flying && player.isShiftKeyDown() && !slowedBySneaking;
        return flyingSneak ? input.scale(0.3F) : input;
    }
}
