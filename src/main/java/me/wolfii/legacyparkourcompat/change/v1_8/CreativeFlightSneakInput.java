package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightSneakInputHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   public void tick() {; lines 13-38.
 *   Source SHA-256: a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd.
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 488-637.
 *   Source SHA-256: 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   public void tick() {; lines 13-50.
 *   Source SHA-256: 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 604-760.
 *   Source SHA-256: 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d.
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
