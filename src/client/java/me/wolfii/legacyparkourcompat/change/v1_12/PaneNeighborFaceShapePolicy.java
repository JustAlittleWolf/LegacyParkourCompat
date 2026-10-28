package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.pane.PaneNeighborEligibilityHook;
import me.wolfii.legacyparkourcompat.change.common.PaneNeighborFaceShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * Minecraft 1.12/1.12.1/1.12.2 (Ornithe Feather Gen2)
 * {@code PaneBlock.resolveVirtualProperties} and {@code shouldConnectTo(Block)}
 * select arms with the directional FaceShape predicate and pane exclusions.
 * Delegating to the existing {@code PaneNeighborFaceShape.connects} preserves
 * that accepted 1.12 provider behavior in the shared neighbor hook.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class PaneNeighborFaceShapePolicy implements PaneNeighborEligibilityHook {
    public PaneNeighborFaceShapePolicy() {}

    @Override
    public Result classify(BlockState neighborState) {
        return Result.NATIVE;
    }

    @Override
    public Optional<Boolean> connects(BlockState neighborState, BlockGetter level, BlockPos pos, Direction face) {
        return PaneNeighborFaceShape.connects(neighborState, level, pos, face);
    }
}
