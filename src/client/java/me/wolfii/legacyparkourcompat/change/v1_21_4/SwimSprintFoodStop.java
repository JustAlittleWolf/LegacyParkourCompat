package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.SwimSprintFoodStopHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Mojmap 1.21.4 LocalPlayer#aiStep and #hasEnoughFoodToStartSprinting keep
 * food failure inside the grounded and sneak exemptions when stopping swim sprint.
 * Mojmap 1.21.5 #shouldStopSwimSprinting and #hasEnoughFoodToSprint make that
 * food failure unconditional. Unobfuscated 26.2 #shouldStopSwimSprinting calls
 * #isSprintingPossible(true), whose unmounted food gate still uses the same
 * greater-than-six-or-flight predicate. The mixin leaves the outer swimming,
 * water, and forward-input gates in place and calls this operation only for the
 * food-dependent sprint-eligibility result. Keep mobility, passenger, and any
 * other native eligibility failure intact.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class SwimSprintFoodStop implements SwimSprintFoodStopHook {
    @Override
    public boolean canSprintForSwimStop(LocalPlayer player, boolean vanillaCanSprint) {
        if (vanillaCanSprint || player.isMobilityRestricted() || player.isPassenger()) {
            return vanillaCanSprint;
        }

        boolean hasEnoughFood = player.getFoodData().getFoodLevel() > 6.0F
            || player.getAbilities().mayfly;
        return hasEnoughFood ? false : player.onGround() || player.input.keyPresses.shift();
    }
}
