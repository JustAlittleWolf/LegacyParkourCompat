package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import net.minecraft.resources.Identifier;

import java.util.UUID;

/** Proven identifier-to-UUID correspondences for the three vanilla speed modifiers. */
public final class MovementSpeedModifierIdentity {
    private static final Identifier SPEED = Identifier.withDefaultNamespace("effect.speed");
    private static final Identifier SLOWNESS = Identifier.withDefaultNamespace("effect.slowness");
    private static final Identifier SPRINTING = Identifier.withDefaultNamespace("sprinting");

    private MovementSpeedModifierIdentity() {
    }

    public static UUID oldUuid(Identifier identifier) {
        if (SPEED.equals(identifier)) {
            return UUID.fromString("91AEAA56-376B-4498-935B-2F7F68070635");
        }
        if (SLOWNESS.equals(identifier)) {
            return UUID.fromString("7107DE5E-7CE8-4030-940E-514C1F160890");
        }
        if (SPRINTING.equals(identifier)) {
            return UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
        }
        return null;
    }
}
