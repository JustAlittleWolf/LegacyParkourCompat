package me.wolfii.legacyparkourcompat.change.v1_13_1.player;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PushAwayBlockingHook;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * Minecraft 1.13, 1.13.1 and 1.13.2 Ornithe Feather:
 * LocalClientPlayerEntity.wouldCollideAt uses PlayerEntity.shouldSuffocate
 * for the swimming origin and doesNotSuffocate for the non-swimming origin.
 * The cardinal candidates in pushAwayFrom always use doesNotSuffocate.
 * Both predicates read Block.isSolid, which excludes signal sources including
 * the registered RedstoneBlock. Stable 1.14 Ornithe Feather switches this
 * probe to PlayerEntity.shouldSuffocate / Block.isViewBlocking, which includes
 * the redstone block. The 26.2 unobfuscated LocalPlayer.moveTowardsClosestSpace
 * calls suffocatesAt for the origin and candidate columns.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class RedstonePushAway implements PushAwayBlockingHook {
    @Override
    public Optional<Boolean> isBlocking(BlockState state) {
        return state.is(Blocks.REDSTONE_BLOCK) ? Optional.of(false) : Optional.empty();
    }
}
