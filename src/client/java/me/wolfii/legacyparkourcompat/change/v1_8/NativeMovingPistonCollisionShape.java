package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.MovingPistonCollisionShapeHook;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.8.9, ornithe-feather build.2: MovingBlock.getCollisionShape trims a moved
 * state's collision box using last progress; it does not use the current/last
 * outline union of 1.10.2 MovingBlockEntity.getShape (ornithe-feather build.2).
 * Preserve native behavior until that distinct earlier operation is implemented,
 * preventing the closest-later resolver from applying the 1.9-1.10.2 union.
 * 26.2, unobfuscated: native MovingPistonBlock.getCollisionShape delegates to
 * PistonMovingBlockEntity.getCollisionShape.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class NativeMovingPistonCollisionShape implements MovingPistonCollisionShapeHook {
    @Override
    public Optional<VoxelShape> collisionShape(BlockState movedState, Direction direction,
                                              boolean extending, boolean source,
                                              float progress, float previousProgress) {
        return Optional.empty();
    }
}
