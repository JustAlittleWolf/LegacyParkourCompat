package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.13.2 Ornithe Feather Block.getFaceShape yields SOLID for Soul Sand and
 * FenceBlock.shouldConnectTo includes that arm. 26.2 unobfuscated
 * SoulSandBlock.getBlockSupportShape makes the same arm native for the
 * ordinary fixture. This boundary keeps the later rail omission out of 1.13.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class NativeOakFenceSoulSandCollisionShape implements BlockCollisionShapeHook {
    @Override
    public String blockId() {
        return "minecraft:oak_fence";
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                               CollisionContext context) {
        return Optional.empty();
    }
}
