package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.push.PlayerPushabilityHook;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 1.10, 1.10.1 and 1.10.2, ornithe-feather: LivingEntity.isPushable returns
 * !removed; 1.11, 1.11.1 and 1.11.2 change it to isAlive() && !isClimbing().
 * LivingEntity.isClimbing/canClimbTrapdoor floor x, bounding-box minY and z,
 * recognize ladder/vine or an open trapdoor above a matching-facing ladder.
 * EntityFilter.canBePushedBy admits only the local player on clients, and
 * RemoteClientPlayerEntity.mobTick reaches LivingEntity.pushAwayCollidingEntities.
 *
 * 26.2, unobfuscated: LivingEntity.isPushable excludes onClimbable(), both in
 * EntitySelector.pushableBy and in Entity.push(Entity)'s recipient impulse gate.
 * Restore only the accepted awake, live local-player climbing case. Keep native health,
 * spectator and modern-block behavior; remote entity physics is outside scope.
 * The existing query, team, sleeping, vehicle, noPhysics and impulse gates stay
 * in EntitySelector.pushableBy, LivingEntity.push(Entity) and Entity.push(Entity).
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class ClimbingPlayerPushability implements PlayerPushabilityHook {
    @Override
    public boolean isPushable(Player player, boolean vanilla) {
        if (vanilla || !player.isLocalPlayer() || !player.isAlive() || player.isSpectator() || player.isSleeping()) {
            return vanilla;
        }
        BlockPos pos = new BlockPos(
            Mth.floor(player.getX()), Mth.floor(player.getBoundingBox().minY), Mth.floor(player.getZ())
        );
        BlockState state = player.level().getBlockState(pos);
        if (state.is(Blocks.LADDER) || state.is(Blocks.VINE)) {
            return true;
        }
        // Only the two era-existing trapdoors receive historical behavior.
        if ((state.is(Blocks.OAK_TRAPDOOR) || state.is(Blocks.IRON_TRAPDOOR))
            && state.getValue(TrapDoorBlock.OPEN)) {
            BlockState below = player.level().getBlockState(pos.below());
            return below.is(Blocks.LADDER)
                && below.getValue(LadderBlock.FACING) == state.getValue(TrapDoorBlock.FACING);
        }
        return vanilla;
    }
}
