package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.ForwardSprintInputHook;

/**
 * 1.21.4, Mojmap: net.minecraft.client.player.LocalPlayer
 * hasEnoughImpulseToStartSprinting uses isUnderWater (returning wasUnderwater)
 * to choose ClientInput.hasForwardImpulse (> 1.0E-5F on forwardImpulse),
 * otherwise forwardImpulse >= the double 0.8. aiStep reads this operation
 * before input.tick and again through canStartSprinting after item/sneak scaling.
 * 1.21.5, Mojmap: LocalPlayer.aiStep and canStartSprinting instead call
 * ClientInput.hasForwardImpulse, testing moveVector.y > 1.0E-5F on the raw
 * normalized input vector without an underwater branch. modifyInput scales a
 * derived vector, so it cannot supply the retained float required here.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class UnderwaterForwardSprintInput implements ForwardSprintInputHook {
    @Override
    public boolean hasEnoughForwardInput(float storedForwardInput, boolean cachedEyeUnderwater) {
        return cachedEyeUnderwater ? storedForwardInput > 1.0E-5F : storedForwardInput >= 0.8;
    }
}
