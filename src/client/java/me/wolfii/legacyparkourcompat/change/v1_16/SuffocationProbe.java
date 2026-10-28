package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SuffocationProbeColumn;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SuffocationProbeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;


/**
 * 1.16.1 Mojmap LocalPlayer.checkInBlock/blocked and Player.freeAt use the
 * default BlockBehaviour.Properties.isSuffocating predicate, which requires a
 * motion-blocking material and a full collision shape. In 1.16.2 Mojmap,
 * Blocks explicitly sets isSuffocating(Blocks::always) for farmland, soul sand,
 * and grass path (the current block is DIRT_PATH). The old column response stays unchanged; only those three
 * target states restore the 1.16.1 predicate result here.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class SuffocationProbe implements SuffocationProbeHook {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        return SuffocationProbeColumn.any(player, pos, cursor -> this.isSuffocating(player, cursor));
    }

    private boolean isSuffocating(Player player, BlockPos pos) {
        BlockState state = player.level().getBlockState(pos);
        if (state.is(Blocks.FARMLAND) || state.is(Blocks.SOUL_SAND) || state.is(Blocks.DIRT_PATH)) {
            return false;
        }
        return state.isSuffocating(player.level(), pos);
    }
}
