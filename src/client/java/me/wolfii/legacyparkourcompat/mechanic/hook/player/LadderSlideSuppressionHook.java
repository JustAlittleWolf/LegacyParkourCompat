package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Resolves the local player's ladder descent suppression predicate. */
@MechanicType("player.ladder-slide-suppression")
public interface LadderSlideSuppressionHook extends VersionedMechanic {
    boolean isSuppressingSlidingDownLadder(LocalPlayer player, boolean nativeValue);
}
