package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SuffocationProbeColumn;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SuffocationProbeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   pushAwayFrom, mobTick.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   addMessage, mobTick.
 * 1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   checkInBlock.
 * 1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   moveTowardsClosestSpace.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class SuffocationProbe implements SuffocationProbeHook {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        return SuffocationProbeColumn.any(player, pos, cursor -> this.isSuffocating(player, target, cursor));
    }

    protected boolean isSuffocating(Player player, ParkourVersion target, BlockPos pos) {
        return player.level().getBlockState(pos).isSuffocating(player.level(), pos);
    }
}
