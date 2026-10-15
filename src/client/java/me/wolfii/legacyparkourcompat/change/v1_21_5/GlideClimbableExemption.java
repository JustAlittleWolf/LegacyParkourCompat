package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GlideClimbableExemptionHook;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mojmap 1.16 and 1.21.10 LivingEntity.onClimbable test climbables without a
 * fall-flight exception. Mojmap 1.21.11 adds isFallFlying && CAN_GLIDE_THROUGH
 * before the native CLIMBABLE branch and lastClimbablePos write; 26.2 retains
 * it. Suppress only that exception for era-existing vanilla vine states.
 * Player.onClimbable's ability-flying and spectator gates remain native, as
 * do the actual CLIMBABLE tag, trapdoor predicate and cache writes. Pre-1.16
 * applicability and custom glide-through tag members retain native behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class GlideClimbableExemption implements GlideClimbableExemptionHook {
    @Override
    public boolean exemptsGlidingPlayer(ParkourVersion selected, BlockState state, boolean gliding) {
        if (selected.olderThan(ParkourVersion.V1_16)) {
            return gliding;
        }
        if (state.is(Blocks.VINE) || state.is(Blocks.TWISTING_VINES) || state.is(Blocks.TWISTING_VINES_PLANT)
            || state.is(Blocks.WEEPING_VINES) || state.is(Blocks.WEEPING_VINES_PLANT)) {
            return false;
        }
        if (selected.newerThanOrEqual(ParkourVersion.V1_17)
            && (state.is(Blocks.CAVE_VINES) || state.is(Blocks.CAVE_VINES_PLANT))) {
            return false;
        }
        return gliding;
    }
}
