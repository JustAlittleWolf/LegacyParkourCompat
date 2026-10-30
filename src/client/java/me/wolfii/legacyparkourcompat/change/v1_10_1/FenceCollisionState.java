package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.FenceCollisionStateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * Keeps native fence collision geometry for the lower resolved profile boundary.
 *
 * <p>1.10.2 Ornithe Feather {@code block/FenceBlock.java}, {@code addCollisions}
 * and {@code shouldConnectTo}, still recompute virtual properties from neighbors;
 * this lower registration intentionally preserves the native 26.2
 * unobfuscated {@code CrossCollisionBlock.getCollisionShape} until the proven
 * 1.11 group rather than extending that group's evidence across the boundary.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class FenceCollisionState implements FenceCollisionStateHook {
    @Override
    public Optional<BlockState> collisionState(BlockState state, BlockGetter level, BlockPos pos) {
        return Optional.empty();
    }
}
