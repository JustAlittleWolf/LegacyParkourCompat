package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalTrapdoors;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.TrapdoorClimbingHook;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 1.8 through 1.8.9 (Ornithe Feather) LivingEntity#isClimbing accepts ladders
 * and vines, but not trapdoors. 1.9 through 1.9.4 add LivingEntity#canClimbTrapdoor
 * for open trapdoors over same-facing ladders. Native 26.2 (unobfuscated)
 * LivingEntity#trapdoorUsableAsLadder retains that condition. Suppressing the
 * match stops onClimbable before its lastClimbablePos assignment. Ornithe Feather
 * 1.8–1.8.9 Blocks registers only trapdoor (oak) and iron_trapdoor.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class OpenTrapdoorClimbing implements TrapdoorClimbingHook {
    public OpenTrapdoorClimbing() {
    }

    @Override
    public boolean blocksClimbing(BlockState state) {
        return HistoricalTrapdoors.existedBefore1_13(state);
    }
}
