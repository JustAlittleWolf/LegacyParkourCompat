package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnJumpStrengthBaseHook;

import java.util.OptionalDouble;

/**
 * Restores the fresh player's registered jump-strength base on a no-keep
 * respawn. In 1.20.6 Mojmap, ClientPacketListener.handleRespawn copies
 * attributes only when shouldKeep((byte) 1) is true, leaving the replacement
 * player's Attributes.JUMP_STRENGTH base at its registered 0.42F default.
 * In 1.21/1.21.1 Mojmap the false branch calls AttributeMap.assignBaseValues,
 * retaining the old base; this override reproduces the earlier behavior on
 * that native path. The float suffix is intentional: the historical default
 * is the double value obtained by widening 0.42F.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class RespawnJumpStrengthBase implements RespawnJumpStrengthBaseHook {
    public RespawnJumpStrengthBase() {
    }

    @Override
    public OptionalDouble noKeepBaseOverride() {
        return OptionalDouble.of(0.42F);
    }
}
