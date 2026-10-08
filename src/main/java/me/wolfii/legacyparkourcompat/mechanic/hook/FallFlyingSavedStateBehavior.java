package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

@MechanicType("player.fall_flying.saved_state")
public interface FallFlyingSavedStateBehavior extends VersionedMechanic {
    boolean shouldPersistFallFlyingState(Player player, ParkourVersion selected);

    void afterFallFlyingStateLoaded(Player player, ParkourVersion selected);
}
