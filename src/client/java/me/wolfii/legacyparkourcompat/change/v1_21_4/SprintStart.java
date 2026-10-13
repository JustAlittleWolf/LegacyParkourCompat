package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintStartHook;
import net.minecraft.world.entity.player.Player;

/**
 * Retains the fall-flying veto introduced by the 1.19.4 sprint eligibility helper.
 * Native 26.2 permits fall-flying sprint entry underwater; this post-query veto
 * removes that newer exception through the 1.21.4 profile.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep key-sprint branch has no fall-flying veto.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   canStartSprinting.
 * 26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java:
 *   canStartSprinting allows fall flight when isUnderWater is true.
 * The independently accepted sprint-during-fall-flying finding covers the adjacent
 * 1.19.3/1.19.4 boundary; this newer native exception explains the retained veto.
 * 1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   canStartSprinting retains the unconditional fall-flying veto.
 * 1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   canStartSprinting first permits fall flight while underwater.
 * The adjacent stable-release predicate boundary is independently source-reviewed.
 * Earlier profiles retain the separately resolved 1.19 no-op override.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class SprintStart implements SprintStartHook {
    @Override
    public boolean canStartSprinting(Player player, boolean vanilla) {
        return vanilla && !player.isFallFlying();
    }
}
