package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * Keeps the native 26.2 ladder shape for profiles before the first accepted
 * source comparison, 1.8.9.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class LadderCollisionNativeFence implements BlockCollisionShapeHook {
    private static final String LADDER = "minecraft:ladder";

    private LadderCollisionNativeFence() {
    }

    @Override
    public String blockId() {
        return LADDER;
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                               CollisionContext context) {
        return Optional.empty();
    }

    @MovementChange.Factory
    public static List<LadderCollisionNativeFence> variants() {
        return List.of(new LadderCollisionNativeFence());
    }
}
