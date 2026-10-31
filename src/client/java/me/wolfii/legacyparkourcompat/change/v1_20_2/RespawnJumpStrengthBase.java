package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnJumpStrengthBaseHook;

import java.util.OptionalDouble;

/**
 * Keeps the no-keep respawn operation native for profiles before the player
 * jump-strength attribute existed. In 1.20.4 Mojmap, Attributes.JUMP_STRENGTH
 * is the horse attribute and LivingEntity.createLivingAttributes does not add
 * it; 1.20.5 Mojmap adds the generic attribute there. This lower no-op anchor
 * prevents the later 1.20.5 respawn delta from being selected for these profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class RespawnJumpStrengthBase implements RespawnJumpStrengthBaseHook {
    public RespawnJumpStrengthBase() {
    }

    @Override
    public OptionalDouble noKeepBaseOverride() {
        return OptionalDouble.empty();
    }
}
