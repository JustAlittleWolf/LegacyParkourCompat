package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalTrapdoors;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterClimbImpulseHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
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
 * last climbable position when a trapdoor passes. The 1.13 through 1.13.2
 * Entity.m_69693160 body scan also admits adjacent water while the open trapdoor
 * in the player's feet cell stays dry. In those exact Ornithe Feather releases,
 * Block's wood and iron trapdoor registrations share TrapdoorBlock.getShape and
 * LivingEntity.isClimbing. The north-facing dry spruce, birch, jungle, acacia,
 * dark oak and iron states therefore match the north-facing ladder fixture.
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
        if (!selected.olderThan(ParkourVersion.V1_13) && isMatchingTrapdoor(player, state)) {
            return false;
        }
        return nativeCheck.get();
    }

    private static boolean isMatchingTrapdoor(Player player, BlockState state) {
        if (!HistoricalTrapdoors.existedIn1_13(state) || !state.getValue(TrapDoorBlock.OPEN)) {
            return false;
        }
        Direction facing = state.getValue(TrapDoorBlock.FACING);
        if (!state.getValue(TrapDoorBlock.WATERLOGGED)
            && (!(player instanceof LocalPlayer) || !OrdinaryPlayerContext.matches(player)
                || player.getAbilities().flying
                || (!state.is(Blocks.OAK_TRAPDOOR) && facing != Direction.NORTH))) {
            return false;
        }
        BlockState below = player.level().getBlockState(player.blockPosition().below());
        return below.is(Blocks.LADDER) && below.getValue(LadderBlock.FACING) == facing;
    }
}
