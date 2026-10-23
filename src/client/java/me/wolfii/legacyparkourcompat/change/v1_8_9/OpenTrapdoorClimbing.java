package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.TrapdoorClimbingHook;

/**
 * 1.8.9 (Ornithe Feather) LivingEntity#isClimbing accepts ladders and vines,
 * but not trapdoors. 1.9 through 1.9.4 add LivingEntity#canClimbTrapdoor for
 * open trapdoors over same-facing ladders. Native 26.2 (unobfuscated)
 * LivingEntity#trapdoorUsableAsLadder retains that condition. Returning false
 * here stops onClimbable before its lastClimbablePos assignment.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class OpenTrapdoorClimbing implements TrapdoorClimbingHook {
    public OpenTrapdoorClimbing() {
    }

    @Override
    public boolean canClimbTrapdoor(boolean vanillaResult) {
        return false;
    }
}
