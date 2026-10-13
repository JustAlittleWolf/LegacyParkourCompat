package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintStartHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   canStartSprinting; lines [[1022, 1030]].
 *   Source SHA-256: 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
 * Candidate locators only: operation fidelity, independent review and profile applicability remain open.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class SprintStart implements SprintStartHook {
    @Override
    public boolean canStartSprinting(Player player, boolean vanilla) {
        return vanilla && !player.isFallFlying();
    }
}
