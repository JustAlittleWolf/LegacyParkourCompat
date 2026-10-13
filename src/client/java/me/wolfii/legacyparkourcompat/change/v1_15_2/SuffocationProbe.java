package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SuffocationProbeColumn;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SuffocationProbeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ShulkerBoxBlock;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   wouldCollideAt scans the player column using shouldSuffocate.
 * 1.14/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   shouldSuffocate reads the negated isViewBlocking predicate.
 * 1.14/ornithe-feather/net/minecraft/block/ShulkerBoxBlock.java:
 *   isViewBlocking returns true regardless of open state.
 * 1.15/mojmap/net/minecraft/world/level/block/ShulkerBoxBlock.java and
 * 1.15.1/mojmap/net/minecraft/world/level/block/ShulkerBoxBlock.java:
 *   isSuffocating returns true regardless of open state.
 * 1.16/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   shulkerBox changes the predicate to blockEntity.isClosed().
 * Earlier profiles do not share this response: 1.13.2/ornithe-feather/
 * net/minecraft/entity/living/player/PlayerEntity.java, shouldSuffocate,
 * reads isSolid; net/minecraft/block/Block.java, isSolid, requires isCube;
 * net/minecraft/block/ShulkerBoxBlock.java, isCube, returns false.
 * This restoration therefore applies from 1.14 through 1.15.2, before the
 * first changed stable release 1.16; older profiles retain their existing path.
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   pushAwayFrom, mobTick.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   addMessage, mobTick.
 * 1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   checkInBlock.
 *   checkInBlock.
 * 1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   moveTowardsClosestSpace.
 *   moveTowardsClosestSpace.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class SuffocationProbe implements SuffocationProbeHook {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        return SuffocationProbeColumn.any(player, pos, cursor -> this.isSuffocating(player, target, cursor));
    }

    private boolean isSuffocating(Player player, ParkourVersion target, BlockPos pos) {
        var blockState = player.level().getBlockState(pos);
        // The resolved old probe includes 1.14 and 1.15, not just the latest endpoint.
        if (target.newerThanOrEqual(ParkourVersion.V1_14)
            && blockState.getBlock() instanceof ShulkerBoxBlock) {
            return true;
        }
        return player.level().getBlockState(pos).isSuffocating(player.level(), pos);
    }
}
