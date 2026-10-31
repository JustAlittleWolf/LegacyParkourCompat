package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import java.util.OptionalDouble;

/**
 * Selects a jump-strength base override for a client player respawn that does
 * not keep attributes. An empty result leaves the native respawn result intact.
 * The client respawn adapter must call this only for its no-keep branch and may
 * write only the returned jump-strength base; it must not restore other attributes.
 */
@MechanicType("player.respawn.jump_strength_base")
public interface RespawnJumpStrengthBaseHook extends VersionedMechanic {
    OptionalDouble noKeepBaseOverride();
}
