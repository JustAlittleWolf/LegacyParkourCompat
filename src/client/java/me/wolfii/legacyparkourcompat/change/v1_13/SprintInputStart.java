package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.DoubleTapSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintKeyStartHook;
import net.minecraft.world.entity.player.Player;

/**
 * 1.13, 1.13.1 and 1.13.2, ornithe-feather:
 * LocalClientPlayerEntity.mobTick adds eye-water support for double taps and
 * rejects body-water held-key starts unless the cached eye-water query is true.
 * 1.12.2, ornithe-feather: the same member requires ground for double taps and
 * has no held-key body-water exclusion. These are conditional start predicates;
 * float-forward eligibility and fluid/eye-state producers resolve separately.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SprintInputStart implements ShallowWaterSprintStartHook, DoubleTapSprintStartHook, SprintKeyStartHook {
    @Override
    public boolean allowShallowWaterStart(Player player, boolean vanilla) {
        return true;
    }

    @Override
    public boolean allowDoubleTapStart(Player player, boolean vanilla) {
        return vanilla && (player.onGround() || player.isUnderWater());
    }

    @Override
    public boolean allowSprintKeyStart(Player player, boolean vanilla) {
        return vanilla && (!player.isInWater() || player.isUnderWater());
    }
}
