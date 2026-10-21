package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightShallowWaterSprintHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.21.8 LocalPlayer.canStartSprinting / shouldStopRunSprinting reject
 * body-water contact without eye-water contact even while flying. Mojmap 1.21.9
 * and 1.21.10 use isSprintingPossible(abilities.flying), bypassing that water
 * condition in flight. Restore only the flying-player operand shared by entry
 * and run-sprint continuation; swim-sprint continuation retains native behavior.
 * Ornithe Feather 1.13 LocalClientPlayerEntity.mobTick introduces the body/eye
 * water sprint predicates; 1.12.2 mobTick has no such water veto. Before that
 * water-sprint feature boundary, retain the independently resolved native input.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class FlightShallowWaterSprint implements FlightShallowWaterSprintHook {
    @Override
    public boolean isSprintingPossible(Player player, ParkourVersion selected, boolean vanilla) {
        if (selected.olderThan(ParkourVersion.V1_13)) {
            return vanilla;
        }
        return vanilla && !(player.getAbilities().flying && player.isInWater() && !player.isUnderWater());
    }
}
