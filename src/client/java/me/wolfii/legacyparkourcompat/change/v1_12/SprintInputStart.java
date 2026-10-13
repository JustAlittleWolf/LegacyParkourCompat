package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.DoubleTapSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintKeyStartHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-WATER-SPRINT is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for water-sprint-gates
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class SprintInputStart implements ShallowWaterSprintStartHook, DoubleTapSprintStartHook, SprintKeyStartHook {
    @Override
    public boolean allowShallowWaterStart(Player player, boolean vanilla) {
        return vanilla;
    }

    @Override
    public boolean allowDoubleTapStart(Player player, boolean vanilla) {
        return vanilla && player.onGround();
    }

    @Override
    public boolean allowSprintKeyStart(Player player, boolean vanilla) {
        return vanilla;
    }
}
