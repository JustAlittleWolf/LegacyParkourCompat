package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalTrapdoors;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterClimbImpulseHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Ornithe Feather 1.13.2 LivingEntity.moveRelative has no post-move climb
 * check in water. In Ornithe Feather 1.14 the same method checks horizontal
 * collision and LivingEntity.isClimbing, then assigns Y = 0.2 before drag.
 * Both isClimbing predicates admit an open trapdoor above a same-facing ladder.
 * The 1.13.2 Blocks and TrapdoorBlock sources identify the era trapdoors and
 * their WATERLOGGED property. Suppress the check for their matching water
 * contact without invoking 26.2 LivingEntity.onClimbable, which writes the
 * last climbable position when a trapdoor passes.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class WaterClimbImpulse implements WaterClimbImpulseHook {
    @Override
    public boolean onClimbable(Player player, ParkourVersion selected, VanillaFn<Boolean> nativeCheck) {
        if (selected.olderThan(ParkourVersion.V1_13) && player.isSwimming()) {
            return nativeCheck.get();
        }
        BlockState state = player.getInBlockState();
        if (state.is(Blocks.LADDER) || state.is(Blocks.VINE)) {
            return false;
        }
        if (!selected.olderThan(ParkourVersion.V1_13) && isMatchingWaterloggedTrapdoor(player, state)) {
            return false;
        }
        return nativeCheck.get();
    }

    private static boolean isMatchingWaterloggedTrapdoor(Player player, BlockState state) {
        if (!HistoricalTrapdoors.existedIn1_13(state)
            || !state.getValue(TrapDoorBlock.OPEN) || !state.getValue(TrapDoorBlock.WATERLOGGED)) {
            return false;
        }
        BlockState below = player.level().getBlockState(player.blockPosition().below());
        return below.is(Blocks.LADDER)
            && below.getValue(LadderBlock.FACING) == state.getValue(TrapDoorBlock.FACING);
    }
}
